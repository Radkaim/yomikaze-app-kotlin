package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryCategoryApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryRequest
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import javax.inject.Inject

class LibraryCategoryRepositoryImpl @Inject constructor(
    private val api: LibraryCategoryApiService
) : LibraryCategoryRepository {

    /**
     * Todo: Implement the function to create a category in library
     */
    override suspend fun createCategory(
        token: String,
        libraryCategoryRequest: LibraryCategoryRequest
    ): Result<LibraryCategoryResponse> {
        return api.createCategory("Bearer $token", libraryCategoryRequest)
    }

    /**
     * Todo: Implement the function to get all categories in library
     */
    override suspend fun getCategories(token: String): Result<BaseResponse<LibraryCategoryResponse>> {
        return try {
            val response = api.getCategories("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}