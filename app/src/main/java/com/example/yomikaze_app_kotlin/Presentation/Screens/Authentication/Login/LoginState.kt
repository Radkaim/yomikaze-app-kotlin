package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

data class LoginState(
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
