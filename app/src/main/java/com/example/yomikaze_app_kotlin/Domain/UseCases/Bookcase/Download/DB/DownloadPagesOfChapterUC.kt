package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB

import android.content.Context
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Repositories.DownloadPageRepository
import javax.inject.Inject

class DownloadPagesOfChapterUC @Inject constructor(
    private val downloadPageRepository: DownloadPageRepository
) {
    suspend fun downloadPagesOfChapter(
        token: String,
        comicId: Long,
        chapter: Chapter,
        context: Context
    ) = downloadPageRepository.downloadPagesOfChapter(token, comicId, chapter, context)
}