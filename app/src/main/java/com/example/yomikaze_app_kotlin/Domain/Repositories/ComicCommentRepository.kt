package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse

interface ComicCommentRepository {

    /**
     * TODO: use for get all comment of comic by comicId
     */
    suspend fun getAllComicCommentByComicId(
        token: String,
        comicId: Long,
        orderBy: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<CommentResponse>>


}