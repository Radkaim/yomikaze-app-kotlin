package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.Page
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PageApiService {

    /**
     * TODO: Implement get list pages of Chapter of a Comic
     */
    @GET("comics/{comicId}/chapters/{chapterNumber}")
    suspend fun getPagesByChapterNumberOfComic(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Path("chapterNumber") chapterNumber: Int
    ) : Response<Page>
}