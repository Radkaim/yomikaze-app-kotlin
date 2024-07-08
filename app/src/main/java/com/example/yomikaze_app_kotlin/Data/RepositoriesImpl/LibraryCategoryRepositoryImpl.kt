package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryCategoryApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryRequest
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryCategoryResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryCategoryRepository
import retrofit2.Response
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

    /**
     * Todo: Implement the function to get comics in category
     */
    override suspend fun getComicsInCategory(
        token: String,
        categoryName: String
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicsInCategory("Bearer $token", categoryName)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Todo: Implement the function to add comic to category
     */
    override suspend fun addComicToCategory(
        token: String,
        libraryId: Long,
        pathRequest: List<PathRequest>
    ): Result<LibraryCategoryResponse> {
        return api.addComicToCategory("Bearer $token", libraryId, pathRequest)
    }

    /**
     * Todo: Implement the function to delete a category in library
     */
    override suspend fun deleteCategory(token: String, categoryId: Long): Response<Unit> {

        val response = api.deleteCategory("Bearer $token", categoryId)
        if (response.isSuccessful) {
            Result.success(Unit)
        }
        return response
    }

    /**
     * Todo: Implement the function to update a category name in library
     */
    override suspend fun updateCategoryName(
        token: String,
        categoryId: Long,
        pathRequest: List<PathRequest>
    ): Response<Unit> {

        val response = api.updateCategoryName("Bearer $token", categoryId, pathRequest)
        if (response.isSuccessful) {
            Result.success(Unit)
        }
        return response
    }
}