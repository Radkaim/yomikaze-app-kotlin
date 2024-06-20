package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

data class LoginState(
    val username: String = "",
    val usernameError: String? = "",
    val password: String = "",
    val passwordError: String? = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)
