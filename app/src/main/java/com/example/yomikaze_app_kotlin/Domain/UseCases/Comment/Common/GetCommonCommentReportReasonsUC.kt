package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.Common

import com.example.yomikaze_app_kotlin.Domain.Models.ReportResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import javax.inject.Inject

class GetCommonCommentReportReasonsUC @Inject constructor(
    private val comicCommentRepository: ComicCommentRepository
) {
    suspend fun getCommonCommentReport(): Result<List<ReportResponse>> {
        return comicCommentRepository.getCommonCommentReportReasons()
    }
}