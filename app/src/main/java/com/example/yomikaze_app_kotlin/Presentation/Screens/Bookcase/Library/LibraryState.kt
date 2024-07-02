package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

data class LibraryState(
    val isLoading: Boolean = false,
    val error: String = "",
    val searchResult: MutableState<List<ComicResponse>> = mutableStateOf(emptyList()),
)