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
            HomeView(navController = navController)
        }
        composable(BottomHomeNavItems.Bookcase.screen_route) {
            BookcaseView(initialTab = 0)
        }


        composable(BottomHomeNavItems.Profile.screen_route) {
            ProfileView(navController, viewModel)
        }
        composable(BottomHomeNavItems.Notification.screen_route) {
            NotificationView()
        }


        //for other screens
         composable("login_route") {
            LoginView(navController = navController)
         }

        // for history tab in bookcase screen
        composable("${BottomHomeNavItems.Bookcase.screen_route}/{param}") { navBackStackEntry ->
            navController.navigate("${BottomHomeNavItems.Bookcase.screen_route}") {
                popUpTo(BottomHomeNavItems.Home.screen_route) { inclusive = true } // pop up to home screen
            }
            val param = navBackStackEntry.arguments?.getString("param")
            when (param) {
                "0" -> BookcaseView(initialTab = 0)
                "1" -> BookcaseView(initialTab = 1)
                "2" -> BookcaseView(initialTab = 2)
                else -> BookcaseView(initialTab = 0)

//            BookcaseView(initialTab = param?.toInt() ?: 0)

            }
        }

    }
}


