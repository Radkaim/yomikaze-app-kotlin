package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ChapterCommentApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReactionRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ChapterCommentRepository
import retrofit2.Response
import javax.inject.Inject

class ChapterCommentRepositoryImpl @Inject constructor(
    private val api: ChapterCommentApiService
) :ChapterCommentRepository {
    override suspend fun getAllChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CommentResponse>> {
        return try {
            val response = api.getAllChapterCommentByComicId("Bearer $token", comicId, chapterNumber, orderBy, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMainChapterCommentByCommentId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long
    ): Result<CommentResponse> {
        return try {
            val response = api.getMainChapterCommentByCommentId("Bearer $token", comicId, chapterNumber, commentId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        content: CommentRequest
    ): Response<CommentResponse> {
        val response = api.postChapterCommentByComicId("Bearer $token", comicId, chapterNumber, content)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("ChapterCommentRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("ChapterCommentRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                }
            }
            Result.failure(Exception("Failed to rate comic"))
        }
        return response

    }

    override suspend fun postReplyChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        content: CommentRequest
    ): Response<CommentResponse> {
        val response = api.postReplyChapterCommentByComicId("Bearer $token", comicId, chapterNumber, commentId, content)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("ChapterCommentRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("ChapterCommentRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                }
            }
            Result.failure(Exception("Failed to rate comic"))
        }
        return response
    }

    override suspend fun getAllReplyCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CommentResponse>> {
        return try {
            val response = api.getAllReplyChapterCommentByComicId("Bearer $token", comicId, chapterNumber,commentId, orderBy, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long
    ): Response<Unit> {
        val response = api.deleteChapterCommentByComicId("Bearer $token", comicId, chapterNumber, commentId)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("ChapterCommentRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("ChapterCommentRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                }
            }
            Result.failure(Exception("Failed to rate comic"))
        }
        return response
    }

    override suspend fun updateChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        pathRequest: List<PathRequest>
    ): Response<Unit> {
        val response = api.updateChapterCommentByComicId("Bearer $token", comicId, chapterNumber, commentId, pathRequest)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("ChapterCommentRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("ChapterCommentRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                }
            }
            Result.failure(Exception("Failed to rate comic"))
        }
        return response
    }

    override suspend fun reactChapterCommentByComicId(
        token: String,
        comicId: Long,
        chapterNumber: Int,
        commentId: Long,
        reactionRequest: ReactionRequest
    ): Response<Unit> {
        val response = api.reactChapterCommentByComicId("Bearer $token", comicId, chapterNumber, commentId, reactionRequest)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("ChapterCommentRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("ChapterCommentRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                }
            }
            Result.failure(Exception("Failed to rate comic"))
        }
        return response
    }


}