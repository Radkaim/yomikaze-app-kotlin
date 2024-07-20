package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.PersonalCategory

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry

data class PersonalCategoryState (

    val listComics: List<LibraryEntry> = emptyList(),
    val isLoadingComicCate : Boolean = true,
    val currentPage: MutableState<Int> = mutableStateOf(0),
    val totalPages: MutableState<Int> = mutableStateOf(0),
    val page: Int = 1,
    val size: Int = 9,

    val isRemoveComicFromCategorySuccess: Boolean = false,
)
