package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment

import com.example.yomikaze_app_kotlin.Domain.Models.ReactionRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import retrofit2.Response
import javax.inject.Inject

class ReactComicCommentByComicIdUC @Inject constructor(
    private val comicCommentRepository: ComicCommentRepository
) {
    suspend fun reactComicCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        reactionRequest: ReactionRequest
    ): Response<Unit> {
        return comicCommentRepository.reactComicCommentByComicId(
            token,
            comicId,
            commentId,
            reactionRequest
        )
    }
}