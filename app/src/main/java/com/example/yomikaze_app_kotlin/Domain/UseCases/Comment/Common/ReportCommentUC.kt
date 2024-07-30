package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.Common

import com.example.yomikaze_app_kotlin.Domain.Models.ReportRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import retrofit2.Response
import javax.inject.Inject

class ReportCommentUC @Inject constructor(
    private val comicCommentRepository: ComicCommentRepository
) {
    suspend fun reportComment(
        token: String,
        commentId: Long,
        reportRequest: ReportRequest
    ): Response<Unit> {
        return comicCommentRepository.reportComment(token, commentId, reportRequest)
    }
}