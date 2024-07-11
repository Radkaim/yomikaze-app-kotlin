package com.example.yomikaze_app_kotlin.Domain.Repositories

import android.content.Context
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter

interface DownloadPageRepository {
    suspend fun downloadPagesOfChapter(
        token: String,
        comicId: Long,
        chapter: Chapter,
        context: Context
    )
}