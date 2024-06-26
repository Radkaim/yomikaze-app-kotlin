package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewFollow

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

data class FollowComicState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val listComicByFollowRanking: List<ComicResponse> = emptyList()
)