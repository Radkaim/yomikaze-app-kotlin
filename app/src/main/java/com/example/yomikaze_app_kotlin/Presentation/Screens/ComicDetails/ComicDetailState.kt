package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Tag

data class ComicDetailState(
    val comicResponse: ComicResponse? = null,
    // val listComicChapters: List<ComicChapter>? = emptyList()
    val listTagGenres: List<Tag>? = emptyList(),
    val listChapters: MutableState<List<Chapter>> = mutableStateOf(emptyList()),
    val isLoading: Boolean = true,
    val isRatingComicSuccess: Boolean = false // Đổi thành Boolean thay vì MutableState<Boolean>
)