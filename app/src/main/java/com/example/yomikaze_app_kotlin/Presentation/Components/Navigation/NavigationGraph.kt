package com.example.yomikaze_app_kotlin.Presentation.Components.Navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.Presentation.Screens.AboutUs.AboutUsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.ViewChapter
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.CoinShop.CoinShopView
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
            RankingView(navController = navController)
        }

        composable("ranking_route/{param}") { navBackStackEntry ->
            val param = navBackStackEntry.arguments?.getString("param")?.toInt()
            when (param) {
                0 -> RankingView(param, navController)
                1 -> RankingView(param, navController)
                2 -> RankingView(param, navController)
                3 -> RankingView(param, navController)
                else -> RankingView(param, navController)
            }
        }

        // comic Details screen
        composable(route = "comic_detail_route/{comicId}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://yomikaze.org/comic_detail/{comicId}"
                    action = Intent.ACTION_VIEW
                }
            )) { navBackStackEntry ->
//
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            ComicDetailsView(comicId = comicId?.toLong() ?: 0, navController)
        }

        //view chapter screen
        composable("view_chapter_route/{comicId}/{chapterNumber}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            val chapterNumber = navBackStackEntry.arguments?.getString("chapterNumber")

            ViewChapter(
                comicId = comicId?.toLong()!!,
                chapterNumber = chapterNumber?.toInt() ?: 0,
                navController = navController
            )
        }

        composable("wifi_route") {
            NetworkDisconnectedDialog()
        }

        composable("edit_profile_route") {
            EditProfileView(navController = navController)
        }

        composable("setting_route") {
            SettingView(navController = navController, viewModel = viewModel)
        }

        composable("aboutUs_route") {
            AboutUsView(navController = navController)
        }
        // for ranking screen
        composable("coins_shop_route") {
            CoinShopView(navController = navController)
        }

    }
}


