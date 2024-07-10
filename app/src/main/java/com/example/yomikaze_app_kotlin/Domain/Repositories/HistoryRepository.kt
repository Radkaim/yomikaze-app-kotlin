package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.HistoryResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import retrofit2.Response

interface HistoryRepository {

    /**
     * TODO: use for get history of user
     */
    suspend fun getHistories(
        token: String,
        page: Int? = null,
        size: Int? = null,
    ): Result<BaseResponse<HistoryResponse>>

    /**
     * TODO: update last read page of a chapter
     */
    suspend fun updateLastReadPage(
        token: String,
        comicId: Long,
        number: Int,
        pathRequest: List<PathRequest>
    ): Response<Unit>

    /**
     * TODO: delete all history of user
     */
    suspend fun deleteAllHistories(
        token: String
    ): Response<Unit>

    /**
     * TODO: delete a history  record of user
     */
    suspend fun deleteHistoryRecord(
        token: String,
        key: Long
    ): Response<Unit>
}