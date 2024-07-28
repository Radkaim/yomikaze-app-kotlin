package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.ReportResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterRepository
import javax.inject.Inject

class GetCommonChapterReportReasonsUC @Inject constructor(
    private val chapterRepository: ChapterRepository
) {
    suspend fun getCommonChapterReportReasons(): Result<List<ReportResponse>> {
        return chapterRepository.getCommonChapterReportReasons()
    }
}