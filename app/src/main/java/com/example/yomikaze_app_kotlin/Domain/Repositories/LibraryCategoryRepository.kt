package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryRequest
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import retrofit2.Response

interface LibraryCategoryRepository {

    /**
     * TODO: Implement the function to create  a category in library
     */
    suspend fun createCategory(
        token: String,
        libraryCategoryRequest: LibraryCategoryRequest
    ): Result<LibraryCategoryResponse>

    /**
     * TODO: Implement the function to get all categories in library
     */
    suspend fun getCategories(
        token: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<LibraryCategoryResponse>>


    /**
     * TODO: Implement the function to delete a category in library
     */
    suspend fun deleteCategory(
        token: String,
        categoryId: Long
    ): Response<Unit>

    /**
     * TODO: Implement the function to update a category name in library
     */
    suspend fun updateCategoryName(
        token: String,
        categoryId: Long,
        pathRequest: List<PathRequest>
    ): Response<Unit>

}