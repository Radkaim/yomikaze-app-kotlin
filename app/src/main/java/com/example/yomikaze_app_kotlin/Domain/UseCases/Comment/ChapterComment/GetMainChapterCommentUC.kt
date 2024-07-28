package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment

import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import javax.inject.Inject

class GetMainChapterCommentUC @Inject constructor(
    private val chapterCommentRepository: ChapterCommentRepository
) {
    suspend fun getMainChapterComment(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long
    ): Result<CommentResponse> {
        return chapterCommentRepository.getMainChapterComment(
            token,
            comicId,
            chapterNumber,
            commentId
        )
    }
}