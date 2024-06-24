package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

data class ProfileState (
    val isUserLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null
)