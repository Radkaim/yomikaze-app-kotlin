package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewRating

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

data class RatingComicState (
    val isLoading: Boolean = true,
    val error: String? = null,
    var listComicByRatingRanking: List<ComicResponse> = emptyList()
)
