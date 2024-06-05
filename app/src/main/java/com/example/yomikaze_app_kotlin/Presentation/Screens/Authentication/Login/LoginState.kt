package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import java.lang.Error

data class LoginState(

    val isLoading: Boolean = true,
    val error: String? = null,
    var hung: String = "",
)
