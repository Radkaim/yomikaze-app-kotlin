package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment

import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateComicCommentByComicIdUC @Inject constructor(
    private val comicCommentRepository: ComicCommentRepository
) {
    suspend fun updateComicComment(
        token: String,
        comicId: Long,
        commentId: Long,
        pathRequest: List<PathRequest>
    ): Response<Unit> {
        return comicCommentRepository.updateComicCommentByComicId(
            token,
            comicId,
            commentId,
            pathRequest
        )
    }
}