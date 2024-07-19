package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewFollow

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

data class FollowComicState(

    val error: String? = null,
    val listComicByFollowRanking: List<ComicResponse> = emptyList(),
    val isLoadingFollow: Boolean = true,
    val currentPage: MutableState<Int> = mutableStateOf(0),
    val totalPages: MutableState<Int> = mutableStateOf(0),
    val page: Int = 1,
    val size: Int = 9,
)