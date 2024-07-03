package com.example.yomikaze_app_kotlin.Domain.Repositories

import android.content.Context
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.Page

interface PageRepository {

    suspend fun getPagesByChapterNumberOfComic(comicId: Long, chapterNumber: Int): Result<Page>

    suspend fun downloadPagesOfChapter(comicId: Long, chapter: Chapter, context: Context)
}