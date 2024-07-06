package com.example.yomikaze_app_kotlin.Presentation.Components.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.BottomHomeNavItems
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.BookcaseView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView.DownloadDetailView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.HomeView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Notification.NotificationView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.ProfileView

fun NavGraphBuilder.homeGraph(viewModel: MainViewModel, navController: NavController) {
    navigation(
        startDestination = BottomHomeNavItems.Home.screen_route,
        route = "main_graph_route"
    ) {
        composable(BottomHomeNavItems.Home.screen_route) {
            HomeView(navController = navController)
        }
        composable(BottomHomeNavItems.Bookcase.screen_route) {
            BookcaseView(navController)
        }
        composable(BottomHomeNavItems.Profile.screen_route) {
            ProfileView(navController, viewModel)
        }
        composable(BottomHomeNavItems.Notification.screen_route) {
            NotificationView()
        }

        // for history tab in bookcase screen
        composable("${BottomHomeNavItems.Bookcase.screen_route}/{param}") { navBackStackEntry ->
            navController.navigate("${BottomHomeNavItems.Bookcase.screen_route}") {
                popUpTo(BottomHomeNavItems.Home.screen_route) {
                    inclusive = true
                } // pop up to home screen
            }
            val param = navBackStackEntry.arguments?.getString("param")
            when (param) {
                "0" -> BookcaseView(navController)
                "1" -> BookcaseView(navController)
                "2" -> BookcaseView(navController)
                else -> BookcaseView(navController)
            }
        }

        composable("download_detail_route/{comicId}/{comicName}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            val comicName = navBackStackEntry.arguments?.getString("comicName")
            DownloadDetailView(
                navController = navController,
                comicId = comicId?.toLong() ?: 0,
                comicName = comicName ?: ""
            )
        }

    }
}