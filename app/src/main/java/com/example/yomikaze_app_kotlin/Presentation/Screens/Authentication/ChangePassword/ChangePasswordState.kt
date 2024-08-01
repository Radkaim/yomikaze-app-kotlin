package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ChangePassword

data class ChangePasswordState(

    val currentPassword: String? = null,
    val currentPasswordError: String? = null,

    val newPassword: String? = null,
    val newPasswordError: String? = null,

    val isChangePasswordSuccess: Boolean = false,

    val isLoading: Boolean = false,

    val error: String? = null,
)
