package com.example.yomikaze_app_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.yomikaze_app_kotlin.Presentation.Home.MainScreenView
import com.example.yomikaze_app_kotlin.Presentation.Splash.SplashScreen
import com.example.yomikaze_app_kotlin.Presentation.theme.YomikazeappkotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YomikazeappkotlinTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController, startDestination = "splash_route") {
                    composable("splash_route") {
                        SplashScreen(navController)
                        // After a delay or when app is ready, navigate to main content
                        // navController.navigate("main_content_route")
                    }
                    composable("main_screen_route") {
                        MainScreenView()
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YomikazeappkotlinTheme { MainScreenView() }}