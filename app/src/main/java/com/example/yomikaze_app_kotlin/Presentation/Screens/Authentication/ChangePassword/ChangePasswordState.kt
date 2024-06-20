package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ChangePassword

data class ChangePasswordState(
    val oldPassword: String,
    val oldPasswordError: String,
    val newPassword: String,
    val newPasswordError: String,
    val confirmPassword: String,
    val confirmPasswordError: String,
    val isLoading: Boolean = true,
    val error: String? = null,
)
