package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment.ChapterComment

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import javax.inject.Inject

class GetAllChapterCommentUC @Inject constructor(
    private val chapterCommentRepository: ChapterCommentRepository
) {
    suspend fun getAllChapterComment(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CommentResponse>> {
        return chapterCommentRepository.getAllChapterComment(
            token,
            comicId,
            chapterNumber,
            orderBy,
            page,
            size
        )
    }
}