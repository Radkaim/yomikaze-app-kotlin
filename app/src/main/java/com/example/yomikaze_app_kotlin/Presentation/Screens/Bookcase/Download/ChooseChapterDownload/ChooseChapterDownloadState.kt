package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload

data class ChooseChapterDownloadState (
    val listNumberChapters: List<Int>? = emptyList(),
    val isListNumberLoading : Boolean = true
)