package com.example.yomikaze_app_kotlin.Presentation.Components.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ChangePassword.ChangePasswordView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ForgotPassword.ForgotPasswordView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login.LoginView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register.RegisterView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ResetPassword.ResetPasswordView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel

fun NavGraphBuilder.authGraph(viewModel: MainViewModel, navController: NavController) {
    navigation(
        startDestination = "login_route",
        route = "auth_graph_route"
    ) {
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
            ForgotPasswordView(navController = navController)
        }

        composable("reset_password_route") {
            ResetPasswordView(navController = navController)
        }
        // for change password screen
        composable("change_password_route") {
            ChangePasswordView(navController = navController)
        }
    }
}