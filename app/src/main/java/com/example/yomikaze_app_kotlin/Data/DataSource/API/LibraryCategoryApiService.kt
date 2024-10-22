package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryRequest
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LibraryCategoryApiService {
    /**
     * TODO: Implement the function to create  a category in library
     */
    @POST("library/categories")
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
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<LibraryCategoryResponse>

    /**
     * TODO: Implement the function to get comics in category
     * fixme**********fixme*****
     */
    @GET("library")
    suspend fun getComicsInCategory(
        @Header("Authorization") token: String,
        @Query("Category") categoryName: String,
        @Query("OrderBy") orderBy: String? = null,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<LibraryEntry>

    /**
     * TODO: Implement the function to delete a category in library
     */
    @DELETE("library/categories/{categoryId}")
    suspend fun deleteCategory(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Long,
    ): Response<Unit>

    /**
     * TODO: Implement the function to update a category name in library
     */
    @PATCH("library/categories/{categoryId}")
    suspend fun updateCategoryName(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Long,
        @Body pathRequest: List<PathRequest>
    ): Response<Unit>

}