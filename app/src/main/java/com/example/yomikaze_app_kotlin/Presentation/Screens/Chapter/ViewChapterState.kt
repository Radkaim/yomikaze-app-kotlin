package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

data class ViewChapterState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val pages: List<String> = emptyList(),
    val isPagesExistInDB: Boolean = true
)
