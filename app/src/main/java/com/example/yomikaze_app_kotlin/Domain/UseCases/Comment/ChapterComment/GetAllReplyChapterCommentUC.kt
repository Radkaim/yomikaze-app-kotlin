package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import javax.inject.Inject

class GetAllReplyChapterCommentUC @Inject constructor(
    private val chapterCommentRepository: ChapterCommentRepository
) {
    suspend fun getAllReplyChapterComment(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CommentResponse>> {
        return chapterCommentRepository.getAllReplyChapterComment(
            token,
            comicId,
            chapterNumber,
            commentId,
            orderBy,
            page,
            size
        )
    }
}
