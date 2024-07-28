package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment

import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import retrofit2.Response
import javax.inject.Inject

class PostReplyChapterCommentUC @Inject constructor(
    private val chapterCommentRepository: ChapterCommentRepository
) {
    suspend fun postReplyChapterComment(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        content: CommentRequest
    ): Response<CommentResponse> {
        return chapterCommentRepository.postReplyChapterComment(
            token,
            comicId,
            chapterNumber,
            commentId,
            content
        )
    }
}