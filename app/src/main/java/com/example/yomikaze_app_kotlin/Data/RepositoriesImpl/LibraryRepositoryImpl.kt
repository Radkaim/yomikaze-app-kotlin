package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val api: LibraryApiService
) : LibraryRepository {

    /**
     * TODO: Implement the function to search comic in all category of library
     */
    override suspend fun searchComicInLibraries(
        token: String,
        name: String
    ): Result<BaseResponse<LibraryEntry>> {
        return try {
            val response = api.searchComicInLibraries("Bearer $token", name)
            Log.d("LibraryRepositoryImpl", "searchComicInLibraries: $response")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to get list comic in library by category name
     * -- view category detail
     */
    override suspend fun getComicsByCategoryName(
        token: String,
        category: String
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicsByCategoryName("Bearer $token", category)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}