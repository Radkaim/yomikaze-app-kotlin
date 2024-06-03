package com.example.yomikaze_app_kotlin.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login.LoginView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.BookcaseView
import com.example.yomikaze_app_kotlin.Presentation.Screens.BottomNav.BottomNavItem
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.HomeView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.ProfileView


@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        // for bottom nav
        composable(BottomNavItem.Home.screen_route) {
            HomeView()
        }
        composable(BottomNavItem.Bookcase.screen_route) {
            BookcaseView()
        }
        composable(BottomNavItem.Profile.screen_route) {
            ProfileView()
        }

        //for other screens
         composable("login_route") {
            LoginView()
         }
    }
}


