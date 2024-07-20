package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import javax.inject.Inject

class GetAllReplyCommentByComicIdUC @Inject constructor(
    private val comicCommentRepository: ComicCommentRepository
) {
    suspend fun getAllReplyCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null
    ) : Result<BaseResponse<CommentResponse>> {
        return comicCommentRepository.getAllReplyCommentByComicId(token, comicId, commentId, orderBy, page, size)
    }
}