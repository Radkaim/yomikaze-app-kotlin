package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.HistoryResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface HistoryApiService {

    /**
     * TODO: use for get history of user
     */
    @GET("history")
    suspend fun getHistories(
        @Header("Authorization") token: String,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<HistoryResponse>

    /**
     * TODO: update last read page of a chapter
     */

    @PATCH("history/comics/{comicId}/chapters/{number}")
    suspend fun updateLastReadPage(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("number") number: Int,
        @Body pathRequest: List<PathRequest>
    ): Response<Unit>

    /**
     * TODO: delete all history of user
     */
    @DELETE("history")
    suspend fun deleteAllHistories(
        @Header("Authorization") token: String
    ): Response<Unit>

    /**
     * TODO: delete a history  record of user
     */
    @DELETE("history/{key}")
    suspend fun deleteHistoryRecord(
        @Header("Authorization") token: String,
        @Path("key") key: Long
    ): Response<Unit>

}