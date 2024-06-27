package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Tag

data class ComicDetailState(
    val comicResponse: ComicResponse? = null,
    // val listComicChapters: List<ComicChapter>? = emptyList()
    val listTagGenres: List<Tag>? = emptyList(),
    val isLoading: Boolean = true,
)