package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.ReportResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetComicCommonReportReasonsUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun getComicCommonReportReasons(): Result<List<ReportResponse>> {
        return comicRepository.getComicCommonReportReasons()
    }
}