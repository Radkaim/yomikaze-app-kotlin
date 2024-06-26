package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewHot

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

data class HotComicState(
    val isLoading: Boolean = true,
    val error: String? = null,
    var listComicByViewRanking: List<ComicResponse> = emptyList()
)