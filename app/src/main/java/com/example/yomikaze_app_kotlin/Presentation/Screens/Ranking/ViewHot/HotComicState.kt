package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewHot

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

data class HotComicState(
    val error: String? = null,
    val listComicByViewRanking: List<ComicResponse> = emptyList(),
    val currentPage: MutableState<Int> = mutableStateOf(0),
    val totalPages: MutableState<Int> = mutableStateOf(0),
    val page: Int = 1,
    val size: Int = 7,
)