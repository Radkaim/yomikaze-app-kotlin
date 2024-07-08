package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch

data class AdvancedSearchState(
    val searchQuery: String = "",
    val searchResults: List<String> = emptyList(),
    val isLoading: Boolean = false
)