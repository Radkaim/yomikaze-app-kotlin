package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewRating

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

data class RatingComicRankingState (
    val error: String? = null,
    var listComicByRatingRanking: List<ComicResponse> = emptyList(),
    val isLoadingRating: Boolean = true,
    val currentPage: MutableState<Int> = mutableStateOf(0),
    val totalPages: MutableState<Int> = mutableStateOf(0),
    val page: Int = 1,
    val size: Int = 9,
)
