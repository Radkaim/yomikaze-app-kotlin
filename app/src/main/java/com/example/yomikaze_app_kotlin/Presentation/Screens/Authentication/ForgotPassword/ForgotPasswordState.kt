package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ForgotPassword

import android.provider.ContactsContract.CommonDataKinds.Email

data class ForgotPasswordState(
    val email: String,
    val emailError: String? = "",
    val isLoading: Boolean = true,
    val error: String? = null,
)
