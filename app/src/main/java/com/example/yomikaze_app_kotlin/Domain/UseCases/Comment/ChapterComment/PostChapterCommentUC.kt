package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment

import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import retrofit2.Response
import javax.inject.Inject

class PostChapterCommentUC @Inject constructor(
    private val chapterCommentRepository: ChapterCommentRepository
) {
    suspend fun postChapterComment(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        content: CommentRequest
    ): Response<CommentResponse> {
        return chapterCommentRepository.postChapterComment(
            token,
            comicId,
            chapterNumber,
            content
        )
    }
}