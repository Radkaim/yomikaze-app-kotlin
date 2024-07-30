package com.example.yomikaze_app_kotlin

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.BottomHomeNavItems
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.authGraph
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.homeGraph
import com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.ViewChapter
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.HomeView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Splash.SplashScreen
import com.example.yomikaze_app_kotlin.ui.AppTheme
import com.example.yomikaze_app_kotlin.ui.YomikazeappkotlinTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //    private val loginViewModel: LoginViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    //    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  WindowCompat.setDecorFitsSystemWindows(window, false)

        //  window.statusBarColor = Color.White.toArgb()
        //   window.navigationBarColor = Color.White.toArgb()

//        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        CoroutineScope(Dispatchers.Main).launch {
            checkAndChangeStatusBarColor(mainViewModel.stateApp.theme, window)
        }


        createNotificationChannel()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
            return
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
//    val msg = getString(R.string.msg_token_fmt, token)
            Log.d("yomikaze_fcm", token)
//    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        })
        setContent {

            // check view model stateApp theme
//            val viewModel: MainViewModel = MainViewModel()
            YomikazeappkotlinTheme(appTheme = mainViewModel.stateApp.theme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "splash_route") {
                        composable("splash_route") {
                            SplashScreen(navController)
                            // After a delay or when app is ready, navigate to main content
                            // navController.navigate("main_content_route")
                        }
                        composable("main_screen_route") {
                            MainView(mainViewModel)
                        }
                        homeGraph(mainViewModel, navController)
                        authGraph(mainViewModel, navController)

                        composable(
                            route = BottomHomeNavItems.Home.screen_route,
                            deepLinks = listOf(
                                navDeepLink {
                                    uriPattern = "https://yomikaze.org/"
                                    action = Intent.ACTION_VIEW
                                }
                            )

                        ) {
                            HomeView(navController = navController)
                        }

                        composable(
                            route = "comic_detail_route/{comicId}",
                            deepLinks = listOf(
                                navDeepLink {
                                    uriPattern = "https://yomikaze.org/comic_detail/{comicId}"
                                    action = Intent.ACTION_VIEW
                                }
                            ),
                            arguments = listOf(navArgument("comicId") { defaultValue = "0" })
                        ) { navBackStackEntry ->
                            val comicId = navBackStackEntry.arguments?.getString("comicId")
                            ComicDetailsView(comicId = comicId?.toLong() ?: 0, navController)
                        }

                        composable(
                            route = "view_chapter_route/{comicId}/{chapterNumber}/{lastPageNumber}",
                            deepLinks = listOf(
                                navDeepLink {
                                    uriPattern =
                                        "https://yomikaze.org/view_chapter/{comicId}/{chapterNumber}"
                                    action = Intent.ACTION_VIEW
                                }
                            ),
                            arguments = listOf(
                                navArgument("comicId") { defaultValue = "0" },
                                navArgument("chapterNumber") { defaultValue = "0" }
                            )
                        ) { navBackStackEntry ->
                            val comicId = navBackStackEntry.arguments?.getString("comicId")
                            val chapterNumber =
                                navBackStackEntry.arguments?.getString("chapterNumber")
                            val lastPageNumber =
                                navBackStackEntry.arguments?.getString("lastPageNumber") ?: "0"

                            ViewChapter(
                                comicId = comicId?.toLong()!!,
                                chapterNumber = chapterNumber?.toInt() ?: 0,
                                lastPageNumber = lastPageNumber.toInt(),
                                navController = navController
                            )
                        }

                    }
                    // Handle deep link intent
                    handleDeepLinkIntent(intent, navController)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Yomikaze Notification Channel"
            val descriptionText = "Yomikaze Notification Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("default", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun handleDeepLinkIntent(intent: Intent?, navController: NavController) {
        intent?.data?.let { uri ->
            val pathSegments = uri.pathSegments
            when {
                pathSegments.size > 2 && pathSegments[0] == "comic_detail" && pathSegments[2] == "chapter" -> {
                    val comicId = pathSegments[1]
                    val chapterId = pathSegments[3]
                    Log.d("DeepLink", "comicId: $comicId, chapterId: $chapterId")
                    navController.navigate("view_chapter_route/$comicId/$chapterId")
                }

                pathSegments.size > 1 && pathSegments[0] == "comics" -> {
                    val comicId = pathSegments[1]
                    Log.d("DeepLink", "comicId: $comicId")
                    navController.navigate("comic_detail_route/$comicId")
                }

                else -> {
                    navController.navigate("home_route")
                }
            }
        }
    }
}


fun checkAndChangeStatusBarColor(
    theme: AppTheme,
//    windowInsetsController: WindowInsetsControllerCompat,
    window: android.view.Window
) {
    val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)

    if (theme == AppTheme.DEFAULT) {
        window.statusBarColor = Color.White.toArgb()
        window.navigationBarColor = Color.White.toArgb()
        windowInsetsController.isAppearanceLightStatusBars =
            true // Điều này sẽ đặt biểu tượng status bar màu tối trên nền sáng
    }
    if (theme == AppTheme.LIGHT) {
        window.statusBarColor = Color.White.toArgb()
        window.navigationBarColor = Color.White.toArgb()
        windowInsetsController.isAppearanceLightStatusBars =
            true // Điều này sẽ đặt biểu tượng status bar màu tối trên nền sáng
    }
    if (theme == AppTheme.DARK) {
        window.statusBarColor = Color.Black.toArgb()
        window.navigationBarColor = Color.Black.toArgb()
        windowInsetsController.isAppearanceLightStatusBars =
            false // Điều này sẽ đặt biểu tượng status bar màu sáng trên nền tối
    }
}

// Declare the launcher at the top of your Activity/Fragment:


//@Preview(showBackground = true)
//@Composable
//fun MainPreview() {
//    val viewModel: MainViewModel = viewModel()
//    //val currentTheme by viewModel.stateApp.collectAsState()
//    YomikazeappkotlinTheme(appTheme = viewModel.stateApp.theme) { MainView(viewModel) }
//}