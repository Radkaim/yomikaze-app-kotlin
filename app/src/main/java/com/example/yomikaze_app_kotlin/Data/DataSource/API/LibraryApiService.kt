package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LibraryApiService {
    /**
     * TODO: Implement the function to search comic in all category of library
     */
    @GET("library")
    fun searchComicInLibraries(
        @Header("Authorization") token: String,
        @Query("Name") name: String,
    ): BaseResponse<ComicResponse>

    /**
     * TODO: Implement the function to get list comic in library by category name
     * -- view category detail
     */
    @GET("library")
    fun getComicsByCategoryName(
        @Header("Authorization") token: String,
        @Query("Category") category: String,
    ): BaseResponse<ComicResponse>
}