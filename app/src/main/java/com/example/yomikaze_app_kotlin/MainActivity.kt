package com.example.yomikaze_app_kotlin

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainScreenView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Splash.SplashScreen
import com.example.yomikaze_app_kotlin.ui.YomikazeappkotlinTheme
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
  class MainActivity : ComponentActivity() {
      override fun onCreate(savedInstanceState: Bundle?) {
          Paper.init(this) // use paperdb show paper.init should be called before any other method

          super.onCreate(savedInstanceState)
          getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // hide status bar
          setContent {

              val viewModel: MainViewModel = MainViewModel()
              YomikazeappkotlinTheme(appTheme = viewModel.stateApp.theme) {
                // A surface container using the 'background' color from the theme
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



@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val viewModel: MainViewModel = viewModel()
    //val currentTheme by viewModel.stateApp.collectAsState()
    YomikazeappkotlinTheme(appTheme = viewModel.stateApp.theme) { MainScreenView(viewModel) }
}