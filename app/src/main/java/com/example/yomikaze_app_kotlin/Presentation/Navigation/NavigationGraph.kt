package com.example.yomikaze_app_kotlin.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yomikaze_app_kotlin.Presentation.Components.BottomNav.BottomHomeNavItems
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login.LoginView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.BookcaseView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.HomeView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Notifi.NotificationView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.ProfileView


@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(navController, startDestination = BottomHomeNavItems.Home.screen_route) {
        // for bottom nav
        composable(BottomHomeNavItems.Home.screen_route) {
            HomeView()
        }
        composable(BottomHomeNavItems.Bookcase.screen_route) {
            BookcaseView()
        }
        composable(BottomHomeNavItems.Profile.screen_route) {
            ProfileView(navController, viewModel)
        }
        composable(BottomHomeNavItems.Notification.screen_route) {
            NotificationView()
        }


        //for other screens
         composable("login_route") {
            LoginView()
         }
    }
}


