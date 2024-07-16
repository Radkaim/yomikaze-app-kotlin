package com.example.yomikaze_app_kotlin.Domain.UseCases.Comment

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import javax.inject.Inject

class GetAllComicCommentByComicIdUC @Inject constructor(
    private val comicCommentRepository: ComicCommentRepository
){
    suspend fun getAllComicCommentByComicId(
        token: String,
        comicId: Long,
        orderBy: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<CommentResponse>>{
        return comicCommentRepository.getAllComicCommentByComicId(token, comicId, orderBy, page, size)
    }
}