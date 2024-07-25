package com.example.yomikaze_app_kotlin.Presentation.Components.Navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.BottomHomeNavItems
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.AdvancedSearchView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.BookcaseView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload.ChooseChapterDownloadView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView.DownloadDetailView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.PersonalCategory.PersonalCateView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment.ComicCommentView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.RelyCommentDetail.RelyCommentDetailView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Home.HomeView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Notification.NotificationView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.ProfileView

fun NavGraphBuilder.homeGraph(viewModel: MainViewModel, navController: NavController) {
    navigation(
        startDestination = BottomHomeNavItems.Home.screen_route,
        route = "main_graph_route"
    ) {
        composable(
            route = BottomHomeNavItems.Home.screen_route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://yomikaze.org/home"
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
            ProfileView(navController, viewModel)
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
                comicNameSearchText = comicNameSearchText
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
    }
}