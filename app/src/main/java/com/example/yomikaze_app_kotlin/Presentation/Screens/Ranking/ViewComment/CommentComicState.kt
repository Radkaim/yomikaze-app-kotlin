package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewComment

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

data class CommentComicState(
    val listComicByCommentRanking: List<ComicResponse> = emptyList(),
    val error: String? = null
)