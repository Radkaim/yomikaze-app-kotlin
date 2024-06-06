package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import java.lang.Error

data class LoginState(
    val username: String = "",
    val usernameError: String? = "",
    val password: String = "",
    val passwordError: String? = "",
    val isLoading: Boolean = true,
    val error: String? = null,
//    var hung: String = "",
)
