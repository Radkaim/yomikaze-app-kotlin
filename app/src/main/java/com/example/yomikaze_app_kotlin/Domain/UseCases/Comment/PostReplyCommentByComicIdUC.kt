package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment

import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import retrofit2.Response
import javax.inject.Inject

class PostReplyCommentByComicIdUC @Inject constructor(
    private val comicCommentRepository: ComicCommentRepository
) {
    suspend fun postReply(
        token: String,
        comicId: Long,
        commentId: Long,
        content: CommentRequest
    ): Response<Unit> {
        return comicCommentRepository.postReplyCommentByComicId(
            token = token,
            comicId = comicId,
            commentId = commentId,
            content = content
        )
    }
}