package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

data class DownloadState(
    val listComicsDB: List<ComicResponse> = emptyList(),

    val isDeleteByIdSuccess: Boolean = true
)
