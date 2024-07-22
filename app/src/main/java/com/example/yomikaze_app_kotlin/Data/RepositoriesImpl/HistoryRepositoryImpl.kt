package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.HistoryApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ContinueReadingResponse
import com.example.yomikaze_app_kotlin.Domain.Models.HistoryResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.HistoryRepository
import retrofit2.Response
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: HistoryApiService
) : HistoryRepository {

    /**
     * TODO: Implement the function to get history
     */
    override suspend fun getHistories(
        token: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<HistoryResponse>> {
        return try {
            val response = api.getHistories("Bearer $token", page, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to update last read page
     */
    override suspend fun updateLastReadPage(
        token: String,
        comicId: Long,
        number: Int,
        pathRequest: List<PathRequest>
    ): Response<Unit> {
        val response = api.updateLastReadPage("Bearer $token", comicId, number, pathRequest)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("HistoryRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("HistoryRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                    Log.e("HistoryRepositoryImpl", "Failed to rate comic code: $httpCode")
                }
            }
            Result.failure(Exception("Failed to get history"))
        }
        return response
    }

    /**
     * TODO: Implement the function to delete all history
     */
    override suspend fun deleteAllHistories(
        token: String
    ): Response<Unit> {
        val response = api.deleteAllHistories("Bearer $token")
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("HistoryRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("HistoryRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                    Log.e("HistoryRepositoryImpl", "Failed to rate comic code: $httpCode")
                }
            }
            Result.failure(Exception("Failed to delete all history"))
        }
        return response
    }

    /**
     * TODO: Implement the function to delete a history record
     */
    override suspend fun deleteHistoryRecord(
        token: String,
        key: Long
    ): Response<Unit> {
        val response = api.deleteHistoryRecord("Bearer $token", key)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("HistoryRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("HistoryRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                    Log.e("HistoryRepositoryImpl", "Failed to rate comic code: $httpCode")
                }
            }
            Result.failure(Exception("Failed to delete history record"))
        }
        return response
    }

    /**
     * TODO: continue reading
     */
    override suspend fun continueReading(
        token: String,
        comicId: Long
    ): Result<ContinueReadingResponse> {
        return try {
            val response = api.continueReading("Bearer $token", comicId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}