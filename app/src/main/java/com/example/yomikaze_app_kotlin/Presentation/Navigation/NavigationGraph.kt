package com.example.yomikaze_app_kotlin.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yomikaze_app_kotlin.Presentation.Components.BottomNav.BottomHomeNavItems
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login.LoginView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register.RegisterView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.BookcaseView
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.HomeView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Notifi.NotificationView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.ProfileView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.RankingView
import com.example.yomikaze_app_kotlin.Presentation.Screens.ViewChapter.ViewChapter


@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(navController, startDestination = BottomHomeNavItems.Home.screen_route) {

        /**
         * Todo for Bottom Navigation
         */
        composable(BottomHomeNavItems.Home.screen_route) {
            HomeView(navController = navController)
        }
        composable(BottomHomeNavItems.Bookcase.screen_route) {
            BookcaseView(initialTab = 0, navController)
        }

        composable(BottomHomeNavItems.Profile.screen_route) {
            ProfileView(navController, viewModel)
        }
        composable(BottomHomeNavItems.Notification.screen_route) {
            NotificationView()
        }


        /**
         * Todo for other screen
         */

        // for login screen
        composable("login_route") {
            LoginView(navController = navController)
        }

        //for register screen
        composable("register_route") {
            RegisterView(navController = navController)
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
                "0" -> BookcaseView(initialTab = 0, navController)
                "1" -> BookcaseView(initialTab = 1, navController)
                "2" -> BookcaseView(initialTab = 2, navController)
                else -> BookcaseView(initialTab = 0, navController)

//            BookcaseView(initialTab = param?.toInt() ?: 0)

            }
        }

        // for ranking screen
        composable("ranking_route") {
            RankingView(initialTab = 0, navController = navController)
        }

        composable("ranking_route/{param}") { navBackStackEntry ->
            val param = navBackStackEntry.arguments?.getString("param")
            when (param) {
                "0" -> RankingView(initialTab = 0, navController)
                "1" -> RankingView(initialTab = 1, navController)
                "2" -> RankingView(initialTab = 2, navController)
                "3" -> RankingView(initialTab = 3, navController)
                else -> RankingView(initialTab = 0, navController)
            }
        }

        // comic Details screen
        composable("comicDetail/{comicId}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            ComicDetailsView(comicId = comicId?.toInt() ?: 0, navController)
        }

        //view chapter screen
        composable("viewChapter/{chapterId}") { navBackStackEntry ->
            val chapterId = navBackStackEntry.arguments?.getString("chapterId")
            ViewChapter(navController = navController, chapterId = chapterId?.toInt() ?: 0 )
        }
    }
}


