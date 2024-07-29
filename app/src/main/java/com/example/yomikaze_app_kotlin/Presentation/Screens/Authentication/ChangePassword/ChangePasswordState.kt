package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ChangePassword

data class ChangePasswordState(

    val oldPassword: String? = null,
    val oldPasswordError: String? = null,

    val newPassword: String? = null,
    val newPasswordError: String? = null,

    val confirmPassword: String? = null,
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = true,
    val error: String? = null,
)
