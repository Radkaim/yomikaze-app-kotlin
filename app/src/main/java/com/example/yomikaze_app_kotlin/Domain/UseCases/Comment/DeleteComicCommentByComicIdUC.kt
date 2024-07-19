package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment

import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteComicCommentByComicIdUC @Inject constructor(
    private val comicCommentRepository: ComicCommentRepository
) {
    suspend fun deleteComicCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long
    ): Response<Unit> {
        return comicCommentRepository.deleteComicCommentByComicId(token, comicId, commentId)
    }
}