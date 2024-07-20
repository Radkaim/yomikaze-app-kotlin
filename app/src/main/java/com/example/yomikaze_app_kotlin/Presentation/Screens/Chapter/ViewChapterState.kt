package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.Page

data class ViewChapterState(
    val isLoading: Boolean = false,
    val isGetPageApiSuccess: Boolean = false,

    val pagesImage: List<String> = emptyList(),
    val pageResponse: Page? = null,

    val isPagesExistInDB: Boolean = false,

    val listChapters: List<Chapter>? = emptyList(),
    val canPreviousChapter: Boolean = false,
    val canNextChapter: Boolean = false,
    val currentChapterNumber: Int = 0,

    val isUserNeedToLogin: Boolean = false,

    val isChapterNeedToUnlock: Boolean = false,
    val chapterUnlockNumber: Int = 0,


)
