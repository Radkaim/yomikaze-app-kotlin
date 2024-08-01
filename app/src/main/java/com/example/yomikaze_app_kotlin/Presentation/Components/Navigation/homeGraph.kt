package com.example.yomikaze_app_kotlin.Presentation.Components.Navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.BottomHomeNavItems
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.AdvancedSearchView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.BookcaseView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload.ChooseChapterDownloadView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView.DownloadDetailView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.PersonalCategory.PersonalCateView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.ViewChapter
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ChapterComment.ChapterCommentView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment.ComicCommentView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.RelyCommentDetail.RelyCommentDetailView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.HomeView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Notification.NotificationView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.ProfileView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.RankingView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.AboutUs.AboutUsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.CoinShop.CoinShopView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.EditProfile.EditProfileView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.SettingView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.TransactionHistory.TransactionHistoryView

fun NavGraphBuilder.homeGraph(viewModel: MainViewModel, navController: NavController) {
    navigation(
        startDestination = BottomHomeNavItems.Home.screen_route,
        route = "main_graph_route"
    ) {
        composable(
            route = BottomHomeNavItems.Home.screen_route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://yomikaze.org"
                    action = Intent.ACTION_VIEW
                }
            )

        ) {
            HomeView(navController = navController)
        }
        composable(BottomHomeNavItems.Bookcase.screen_route) {
            BookcaseView(navController)
        }
        composable(BottomHomeNavItems.Profile.screen_route) {
            ProfileView(navController)
        }
        composable(BottomHomeNavItems.Notification.screen_route) {
            NotificationView(navController = navController)
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

        composable("choose_chapter_download_route/{comicId}/{comicName}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            val comicName = navBackStackEntry.arguments?.getString("comicName")
            ChooseChapterDownloadView(
                navController = navController,
                comicId = comicId?.toLong() ?: 0,
                comicName = comicName ?: ""
            )
        }

        composable("category_detail_route/{categoryId}/{categoryName}") { navBackStackEntry ->
            val categoryId = navBackStackEntry.arguments?.getString("categoryId")
            val categoryName = navBackStackEntry.arguments?.getString("categoryName")
            PersonalCateView(
                navController = navController,
                categoryId = categoryId?.toLong() ?: 0,
                categoryName = categoryName ?: ""
            )
        }

        composable("advance_search_route/{searchText}") { navBackStackEntry ->
            val comicNameSearchText = navBackStackEntry.arguments?.getString("searchText")
            AdvancedSearchView(
                navController = navController,
                comicNameSearchText = comicNameSearchText ?: null
            )
        }

        //comic comment
        composable("comic_comment_route/{comicId}/{comicName}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            val comicName = navBackStackEntry.arguments?.getString("comicName")
            ComicCommentView(
                navController = navController,
                comicId = comicId?.toLong() ?: 0,
                comicName = comicName ?: ""
            )
        }

        // reply comment detail screen
        composable("reply_comment_detail_route/{comicId}/{commentId}/{authorName}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            val commentId = navBackStackEntry.arguments?.getString("commentId")
            val authorName = navBackStackEntry.arguments?.getString("authorName")
            RelyCommentDetailView(
                navController = navController,
                comicId = comicId?.toLong() ?: 0,
                commentId = commentId?.toLong() ?: 0,
                authorName = authorName ?: ""
            )
        }

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
                    uriPattern = "https://yomikaze.org/comics/{comicId}"
                    action = Intent.ACTION_VIEW
                }
            )) { navBackStackEntry ->
//
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            ComicDetailsView(comicId = comicId?.toLong() ?: 0, navController)
        }

        //view chapter screen
        composable("view_chapter_route/{comicId}/{chapterNumber}/{lastPageNumber}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            val chapterNumber = navBackStackEntry.arguments?.getString("chapterNumber")
            val lastPageNumber = navBackStackEntry.arguments?.getString("lastPageNumber") ?: "0"

            ViewChapter(
                comicId = comicId?.toLong()!!,
                chapterNumber = chapterNumber?.toInt() ?: 0,
                lastPageNumber = lastPageNumber.toInt(),
                navController = navController
            )
        }

        //comic comment
        composable("comic_comment_route/{comicId}/{comicName}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            val comicName = navBackStackEntry.arguments?.getString("comicName")
            ComicCommentView(
                navController = navController,
                comicId = comicId?.toLong() ?: 0,
                comicName = comicName ?: ""
            )
        }

        //chapter comment
        composable("chapter_comment_route/{comicId}/{chapterTitle}/{chapterNumber}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            val chapterNumber = navBackStackEntry.arguments?.getString("chapterNumber")
            val chapterTitle = navBackStackEntry.arguments?.getString("chapterTitle")
            ChapterCommentView(
                navController = navController,
                comicId = comicId?.toLong() ?: 0,
                chapterNumber = chapterNumber?.toInt() ?: 0,
                chapterTitle = chapterTitle ?: ""
            )
        }

        // reply comment detail screen
        composable("reply_comment_detail_route/{comicId}/{commentId}/{chapterNumber}/{authorName}") { navBackStackEntry ->
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            val commentId = navBackStackEntry.arguments?.getString("commentId")
            val authorName = navBackStackEntry.arguments?.getString("authorName")
            val chapterNumber = navBackStackEntry.arguments?.getString("chapterNumber") ?: null
            RelyCommentDetailView(
                navController = navController,
                comicId = comicId?.toLong() ?: 0,
                commentId = commentId?.toLong() ?: 0,
                chapterNumber = chapterNumber?.toInt() ?: null,
                authorName = authorName ?: ""
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

        composable("about_us_route") {
            AboutUsView(navController = navController)
        }
        // for ranking screen
        composable("coins_shop_route") {
            CoinShopView(navController = navController)
        }

        //transaction_history_route
        composable("transaction_history_route") {
            TransactionHistoryView(navController = navController)
        }

//        composable("main_screen_route") {
//            MainView(viewModel)
//        }
    }
}