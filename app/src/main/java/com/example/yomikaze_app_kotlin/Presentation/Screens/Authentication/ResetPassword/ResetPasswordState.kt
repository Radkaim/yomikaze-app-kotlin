package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ResetPassword

data class ResetPasswordState(
    val password: String = "",
    val passwordError: String? = "",
    val confirmpassword: String = "",
    val confirmpasswordError: String? = "",
    val isLoading: Boolean = true,
    val error: String? = null,
)
