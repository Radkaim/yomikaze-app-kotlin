package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse


enum class SearchWidgetState {
    OPEN,
    CLOSE
}

data class HomeState(
    val isLoading: Boolean = true,
    val images: List<String> = emptyList(),
    val error: String? = null,
    val isNetworkAvailable : Boolean = true,
    var searchResult: List<ComicResponse> = emptyList()
)