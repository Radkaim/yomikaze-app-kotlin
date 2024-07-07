package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryRequest
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface LibraryCategoryApiService {
    /**
     * TODO: Implement the function to create  a category in library
     */
    @GET("library/categories")
    suspend fun createCategory(
        @Header("Authorization") token: String,
        @Body libraryCategoryRequest: LibraryCategoryRequest,
    ): Result<LibraryCategoryResponse>

    /**
     * TODO: Implement the function to get all categories in library
     */
    @GET("library/categories")
    suspend fun getCategories(
        @Header("Authorization") token: String,
    ): BaseResponse<LibraryCategoryResponse>

}