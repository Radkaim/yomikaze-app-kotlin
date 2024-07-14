package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter

data class DownloadDetailsState(
    val listChapterDownloaded: List<Chapter> = emptyList(),
    val isEditMode: Boolean = false,
    val isDeleteSuccess: Boolean = false,
)