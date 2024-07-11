package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * ChapterApiService is an interface that defines the API endpoints for the chapter service.
 * The interface is used by Retrofit to generate the implementation of the API endpoints.
 */
interface ChapterApiService {
    @GET("comics/{comicId}/chapters")
    suspend fun getListChaptersByComicId(@Path("comicId") comicId: Long): List<Chapter>

    @GET("comics/{comicId}/chapters/{chapterNumber}")
    suspend fun getChapterDetail(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("chapterNumber") chapterNumber: Int
    ): Chapter

}