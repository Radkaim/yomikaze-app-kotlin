package com.example.yomikaze_app_kotlin.Presentation.Screens.Home


data class HomeState(
    val isLoading: Boolean = true,
    val images: List<String> = emptyList(),
    val error: String? = null,
    val isUserLoggedIn: Boolean = false,
    val isNetworkAvailable : Boolean = true
)