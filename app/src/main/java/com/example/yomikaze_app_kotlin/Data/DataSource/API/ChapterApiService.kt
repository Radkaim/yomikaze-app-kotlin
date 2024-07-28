package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.ReportRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReportResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * ChapterApiService is an interface that defines the API endpoints for the chapter service.
 * The interface is used by Retrofit to generate the implementation of the API endpoints.
 */
interface ChapterApiService {
    @GET("comics/{comicId}/chapters")
    suspend fun getListChaptersByComicId(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Query("Pagination") pagination: Boolean = false,
    ): BaseResponse<Chapter>

    @GET("comics/{comicId}/chapters/{chapterNumber}")
    suspend fun getChapterDetail(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("chapterNumber") chapterNumber: Int
    ): Chapter

    /**
     * TODO: unlock a chapter
     */
    @PUT("comics/{comicId}/chapters/{chapterNumber}/unlock")
    suspend fun unlockAChapter(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("chapterNumber") chapterNumber: Int
    ): Response<Unit>

    /**
     * TODO: Unlock many chapters
     */
    @PUT("comics/{comicId}/chapters/unlock")
    suspend fun unlockManyChapters(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Body chapterNumbers: List<Int>
    ): Response<Unit>

    /**
     * TODO: get common report reasons of chapter
     */
    @GET("reports/chapter/reasons")
    suspend fun getCommonChapterReportReasons(): List<ReportResponse>

    /**
     * TODO: use for report a chapter
     */
    @POST("reports/chapter/comics/{comicId}/chapters/{chapterNumber}")
    suspend fun reportChapter(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("chapterNumber") chapterNumber: Int,
        @Body reportReason: ReportRequest
    ): Response<Unit>
}