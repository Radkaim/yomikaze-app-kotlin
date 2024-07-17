package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicCommentApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import javax.inject.Inject

class ComicCommentRepositoryImpl @Inject constructor(
    private val api: ComicCommentApiService
) : ComicCommentRepository {

    override suspend fun getAllComicCommentByComicId(
        token: String,
        comicId: Long,
        orderBy: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CommentResponse>> {
        return try {
            val response = api.getAllComicCommentByComicId("Bearer $token", comicId, orderBy, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Log.e("CommentComicRepositoryImpl", "getAllComicCommentByComicId: $e")
            Result.failure(e)
        }
    }

    override suspend fun postComicCommentByComicId(
        token: String,
        comicId: Long,
        content: String
    ): Result<CommentResponse> {
        return try {
            val response = api.postComicCommentByComicId("Bearer $token", comicId, content)
            Result.success(response)
        } catch (e: Exception) {
            Log.e("CommentComicRepositoryImpl", "postComicCommentByComicId: $e")
            Result.failure(e)
        }
    }

}