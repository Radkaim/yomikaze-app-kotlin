package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

data class LoginState(
    val isLoading: Boolean = true,
    val error: String? = null,
    var hung: String = "",
)