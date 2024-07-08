package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry

data class LibraryState(
    val isSearchLoading: Boolean = false,
    val error: String = "",

    val searchResult: MutableState<List<LibraryEntry>> = mutableStateOf(emptyList()),
    val totalSearchResults: Int? = 0,

    //category
    val categoryList: List<LibraryCategoryResponse> = emptyList(),
    val totalCategoryResults: Int? = 0,
    val isCategoryLoading: Boolean = false,

    //create category
    val isCreateCategorySuccess: Boolean = true,

    val imageCoverOfCate: String = "",
)