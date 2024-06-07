package com.example.yomikaze_app_kotlin

import android.annotation.SuppressLint
import android.content.ClipData
import android.media.RouteListingPreference.Item
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Presentation.Components.BottomNav.BottomHomeNavItems.Bookcase.title
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.ComicCardItem
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainScreenView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Splash.SplashScreen
import com.example.yomikaze_app_kotlin.R.drawable.*
import com.example.yomikaze_app_kotlin.ui.AppTheme
import com.example.yomikaze_app_kotlin.ui.YomikazeappkotlinTheme
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        Paper.init(this) // use paperdb show paper.init should be called before any other method

        super.onCreate(savedInstanceState)
      //  WindowCompat.setDecorFitsSystemWindows(window, false)

      //  window.statusBarColor = Color.White.toArgb()
     //   window.navigationBarColor = Color.White.toArgb()

        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)


        setContent {

            // check view model stateApp theme
            val viewModel: MainViewModel = MainViewModel()
            checkAndChangeStatusBarColor(viewModel.stateApp.theme, windowInsetsController, window)

            YomikazeappkotlinTheme(appTheme = viewModel.stateApp.theme) {
                // A surface container using the 'background' color from the theme
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    val navController = rememberNavController()
                    Paper.init(navController.context.applicationContext)
                    NavHost(navController, startDestination = "splash_route") {
                        composable("splash_route") {
                            SplashScreen(navController)
                            // After a delay or when app is ready, navigate to main content
                            // navController.navigate("main_content_route")
                        }
                        composable("main_screen_route") {
                            MainScreenView(viewModel)
                        }
                    }
                }

            }
        }
    }
}

fun checkAndChangeStatusBarColor(theme: AppTheme, windowInsetsController: WindowInsetsControllerCompat, window: android.view.Window) {

    if (theme == AppTheme.DEFAULT){
        window.statusBarColor = Color.White.toArgb()
        window.navigationBarColor = Color.White.toArgb()
        windowInsetsController.isAppearanceLightStatusBars = true // Điều này sẽ đặt biểu tượng status bar màu tối trên nền sáng
    }
    if (theme == AppTheme.LIGHT) {
        window.statusBarColor = Color.White.toArgb()
        window.navigationBarColor = Color.White.toArgb()
        windowInsetsController.isAppearanceLightStatusBars = true // Điều này sẽ đặt biểu tượng status bar màu tối trên nền sáng
    }
    if(theme == AppTheme.DARK) {
        window.statusBarColor = Color.Black.toArgb()
        window.navigationBarColor = Color.Black.toArgb()
        windowInsetsController.isAppearanceLightStatusBars = false // Điều này sẽ đặt biểu tượng status bar màu sáng trên nền tối
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val viewModel: MainViewModel = viewModel()
    //val currentTheme by viewModel.stateApp.collectAsState()
    YomikazeappkotlinTheme(appTheme = viewModel.stateApp.theme) { MainScreenView(viewModel) }
}