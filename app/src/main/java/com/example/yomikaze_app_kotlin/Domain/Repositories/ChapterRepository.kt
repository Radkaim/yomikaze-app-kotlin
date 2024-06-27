package com.example.yomikaze_app_kotlin.Domain.Repositories

import android.content.Context
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter

interface ChapterRepository {
    suspend fun downloadPagesOfChapter(chapter: Chapter, context: Context)
}