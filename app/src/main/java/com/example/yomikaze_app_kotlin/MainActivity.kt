package com.example.yomikaze_app_kotlin

import android.content.Intent
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
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.ViewChapter
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Splash.SplashScreen
import com.example.yomikaze_app_kotlin.ui.AppTheme
import com.example.yomikaze_app_kotlin.ui.YomikazeappkotlinTheme
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

                pathSegments.size > 1 && pathSegments[0] == "comic_detail" -> {
                    val comicId = pathSegments[1]
                    Log.d("DeepLink", "comicId: $comicId")
                    navController.navigate("comic_detail_route/$comicId")
                }

                else -> {
                    navController.navigate("main_graph_route")
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


//@Preview(showBackground = true)
//@Composable
//fun MainPreview() {
//    val viewModel: MainViewModel = viewModel()
//    //val currentTheme by viewModel.stateApp.collectAsState()
//    YomikazeappkotlinTheme(appTheme = viewModel.stateApp.theme) { MainView(viewModel) }
//}