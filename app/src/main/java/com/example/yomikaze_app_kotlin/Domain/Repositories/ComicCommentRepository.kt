package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import retrofit2.Response

interface ComicCommentRepository {

    /**
     * TODO: use for get all comment of comic by comicId
     */
    suspend fun getAllComicCommentByComicId(
        token: String,
        comicId: Long,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<CommentResponse>>

    /**
     * TODO: use for post comment of comic by comicId
     */
    suspend fun postComicCommentByComicId(
        token: String,
        comicId: Long,
        content: CommentRequest
    ): Response<Unit>

    /**
     * TODO: use for delete comic comment by comicId and commentId
     */
    suspend fun deleteComicCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long
    ): Response<Unit>

}