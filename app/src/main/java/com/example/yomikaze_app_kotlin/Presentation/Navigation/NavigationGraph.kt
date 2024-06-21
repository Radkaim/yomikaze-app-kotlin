package com.example.yomikaze_app_kotlin.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialogs.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.ViewChapter
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.EditProfile.EditProfileView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.Setting.SettingView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.RankingView


@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    NavHost(navController, startDestination = "main_graph_route") {

        /**
         * Todo for home Navigation
         */
        homeGraph(viewModel, navController)

        /**
         * Todo for auth screen
         */
        authGraph(viewModel, navController)

        /**
         * Todo for other Navigation
         */


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
        composable("comic_detail_route/{comicId}") { navBackStackEntry ->
//
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            ComicDetailsView(comicId = comicId?.toInt() ?: 0, navController)
        }

        //view chapter screen
        composable("view_chapter_route/{chapterId}") { navBackStackEntry ->
            val chapterId = navBackStackEntry.arguments?.getString("chapterId")
            ViewChapter(navController = navController, chapterId = chapterId?.toInt() ?: 0 )
        }

        composable("wifi_route"){
            NetworkDisconnectedDialog()
        }
    }
}


