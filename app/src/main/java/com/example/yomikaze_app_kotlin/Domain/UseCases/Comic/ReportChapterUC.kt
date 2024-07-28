package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.ReportRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import retrofit2.Response
import javax.inject.Inject

class ReportChapterUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun reportChapter(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        reportRequest: ReportRequest
    ): Response<Unit> {
        return chapterRepository.reportChapter(
            token,
            comicId,
            chapterNumber,
            reportRequest
        )
    }
}