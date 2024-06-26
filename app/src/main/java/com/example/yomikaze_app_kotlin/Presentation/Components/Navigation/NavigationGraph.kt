package com.example.yomikaze_app_kotlin.Presentation.Components.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.Presentation.Screens.AboutUs.AboutUsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ChangePassword.ChangePasswordView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ForgotPassword.ForgotPasswordView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login.LoginView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register.RegisterView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ResetPassword.ResetPasswordView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.ViewChapter
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailsView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.EditProfile.EditProfileView
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
            val param = navBackStackEntry.arguments?.getString("param")
            when (param) {
                "0" -> RankingView(navController)
                "1" -> RankingView(navController)
                "2" -> RankingView(navController)
                "3" -> RankingView(navController)
                else -> RankingView(navController)
            }
        }

        // comic Details screen
        composable("comic_detail_route/{comicId}") { navBackStackEntry ->
//
            val comicId = navBackStackEntry.arguments?.getString("comicId")
            ComicDetailsView(comicId = comicId?.toLong() ?: 0, navController)
        }

        //view chapter screen
        composable("view_chapter_route/{chapterId}") { navBackStackEntry ->
            val chapterId = navBackStackEntry.arguments?.getString("chapterId")
            ViewChapter(navController = navController, chapterId = chapterId?.toInt() ?: 0)
        }

        composable("wifi_route") {
            NetworkDisconnectedDialog()
        }
        // for login screen
        composable("login_route") {
            LoginView(navController = navController)
        }

        //for register screen
        composable("register_route") {
            RegisterView(navController = navController)
        }
        //for forgot password screen
        composable("forgot_password_route") {
            ForgotPasswordView(navController = navController) }

        composable("reset_password_route") {
            ResetPasswordView(navController = navController)
        }

        // for change password screen
        composable("change_password_route") {
            ChangePasswordView(navController = navController)
        }

        composable("edit_profile_route") {
            EditProfileView(navController = navController)
        }

        composable("aboutUs_route") {
            AboutUsView(navController = navController)
        }
        // for ranking screen
        composable("ranking_route") {
            RankingView(navController = navController)
        }

        composable("ranking_route/{param}") { navBackStackEntry ->
            val param = navBackStackEntry.arguments?.getString("param")
            when (param) {
                "0" -> RankingView( navController)
                "1" -> RankingView( navController)
                "2" -> RankingView( navController)
                "3" -> RankingView( navController)
                else -> RankingView( navController)
            }
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


