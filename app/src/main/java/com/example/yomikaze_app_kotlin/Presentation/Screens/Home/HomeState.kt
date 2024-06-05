package com.example.yomikaze_app_kotlin.Presentation.Screens.Home


data class HomeState (
    val isLoading: Boolean = false,
    val images: List<String> = emptyList(),
    val error: String? = null
)