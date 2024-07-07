package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry

data class LibraryState(
    val isSearchLoading: Boolean = false,
    val error: String = "",
    val totalResults: Int? = 0,
    val searchResult: MutableState<List<LibraryEntry>> = mutableStateOf(emptyList()),
)