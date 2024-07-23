package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment

import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import javax.inject.Inject

class GetMainCommentByCommentIdUC @Inject constructor(
    private val comicCommentRepository: ComicCommentRepository
) {
    suspend fun getMainCommentByCommentId(token: String, comicId: Long, commentId: Long): Result<CommentResponse> {
        return comicCommentRepository.getMainCommentByCommentId(token, comicId, commentId)
    }
}