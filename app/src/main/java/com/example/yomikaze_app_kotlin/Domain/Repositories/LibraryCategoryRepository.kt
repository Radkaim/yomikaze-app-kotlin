package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryRequest
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse

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
    suspend fun getCategories(token: String): Result<BaseResponse<LibraryCategoryResponse>>

    /**
     * TODO: Implement the function to delete a category in library
     */
}