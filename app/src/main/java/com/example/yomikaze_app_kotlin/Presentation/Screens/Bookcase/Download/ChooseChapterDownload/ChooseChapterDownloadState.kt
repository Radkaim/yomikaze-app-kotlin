package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter

data class ChooseChapterDownloadState (
    val listNumberChapters: List<Int>? = emptyList(),
    val isListNumberLoading : Boolean = true,

    val listChapterForDownloaded: List<Chapter> = emptyList(),
    val isPrepareForDownload: Boolean = false,

    val isUnlockChapterSuccess: Boolean = false,
)