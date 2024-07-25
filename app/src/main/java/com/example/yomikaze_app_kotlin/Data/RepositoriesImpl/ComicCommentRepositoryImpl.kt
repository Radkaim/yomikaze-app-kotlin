package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicCommentApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CommentRequest
import com.example.yomikaze_app_kotlin.Domain.Models.CommentResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReactionRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicCommentRepository
import retrofit2.Response
import javax.inject.Inject

class ComicCommentRepositoryImpl @Inject constructor(
    private val api: ComicCommentApiService
) : ComicCommentRepository {

    override suspend fun getAllComicCommentByComicId(
        token: String,
        comicId: Long,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CommentResponse>> {
        return try {
            val response =
                api.getAllComicCommentByComicId("Bearer $token", comicId, orderBy, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Log.e("CommentComicRepositoryImpl", "getAllComicCommentByComicId: $e")
            Result.failure(e)
        }
    }

    /**
     * TODO: use for get comment by comicId and commentId
     */
    override suspend fun getMainCommentByCommentId(
        token: String,
        comicId: Long,
        commentId: Long
    ): Result<CommentResponse> {
        return try {
            val response = api.getMainCommentByCommentId("Bearer $token", comicId, commentId)
            Result.success(response)
        } catch (e: Exception) {
            Log.e("CommentComicRepositoryImpl", "getMainCommentByCommentId: $e")
            Result.failure(e)
        }
    }

    override suspend fun postComicCommentByComicId(
        token: String,
        comicId: Long,
        content: CommentRequest
    ): Response<CommentResponse> {
        val response = api.postComicCommentByComicId("Bearer $token", comicId, content)
        if (response.isSuccessful) {
            Log.d("CommentComicRepositoryImpl", "postComicCommentByComicId: ${response.body()}")
            Result.success(Unit)
        } else {
//            val httpCode = response.code()
//            when (httpCode) {
//                400 -> {
//                    // Xử lý lỗi 400 (Bad Request)
//                    Log.e("CommentComicRepositoryImpl", "Bad Request")
//                }
//
//                401 -> {
//                    // Xử lý lỗi 401 (Unauthorized)
//                    Log.e("CommentComicRepositoryImpl", "Unauthorized")
//                }
//                // Xử lý các mã lỗi khác
//                else -> {
//                    // Xử lý mặc định cho các mã lỗi khác
//                }
//            }
            Result.failure(Exception("Failed to rate comic"))
        }
        return response
    }

    /**
     * TODO: use for post reply comment by comicId and commentId
     */
    override suspend fun postReplyCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        content: CommentRequest
    ): Response<CommentResponse> {
        val response = api.postReplyCommentByComicId("Bearer $token", comicId, commentId, content)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("CommentComicRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("CommentComicRepositoryImpl", "Unauthorized")
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

    /**
     * TODO: use for get all reply comment by comicId and commentId
     */
    override suspend fun getAllReplyCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CommentResponse>> {
        return try {
            val response =
                api.getAllReplyCommentByComicId(
                    "Bearer $token",
                    comicId,
                    commentId,
                    orderBy,
                    page,
                    size
                )
            Result.success(response)
        } catch (e: Exception) {
            Log.e("CommentComicRepositoryImpl", "getAllReplyCommentByComicId: $e")
            Result.failure(e)
        }
    }

    /**
     * TODO: use for delete comic comment by comicId and commentId
     */
    override suspend fun deleteComicCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long
    ): Response<Unit> {
        val response = api.deleteComicCommentByComicId("Bearer $token", comicId, commentId)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("CommentComicRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("CommentComicRepositoryImpl", "Unauthorized")
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

    /**
     * TODO: use for update comic comment by comicId and commentId
     */
    override suspend fun updateComicCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        pathRequest: List<PathRequest>
    ): Response<Unit> {
        val response =
            api.updateComicCommentByComicId("Bearer $token", comicId, commentId, pathRequest)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("CommentComicRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("CommentComicRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                    Log.e("CommentComicRepositoryImpl", "Failed to update comic comment $httpCode")
                }
            }
            Result.failure(Exception("Failed to rate comic"))
        }
        return response
    }

    /**
     * TODO: use for react comic comment by comicId and commentId
     */
    override suspend fun reactComicCommentByComicId(
        token: String,
        comicId: Long,
        commentId: Long,
        reactionRequest: ReactionRequest
    ): Response<Unit> {
        val response = api.reactComicCommentByComicId("Bearer $token", comicId, commentId, reactionRequest)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("CommentComicRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("CommentComicRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                    Log.e("CommentComicRepositoryImpl", "Failed to react comic comment $httpCode")
                }
            }
            Result.failure(Exception("Failed to rate comic"))
        }
        return response
    }
}