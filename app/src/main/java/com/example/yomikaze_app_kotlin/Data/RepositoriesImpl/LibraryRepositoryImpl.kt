package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LibraryApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.LibraryRepository
import retrofit2.Response
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
    override suspend fun getComicsByCategoryId(
        token: String,
        category: Long,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<LibraryEntry>> {
        return try {
            val response = api.getComicsByCategoryId("Bearer $token", category, orderBy, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TODO: Implement the function to add a comic to library category
     * in the first time they follow the comic
     */
    override suspend fun addComicToLibraryFirstTime(
        token: String,
        libraryRequest: LibraryRequest,
    ): Response<Unit> {
        val response = api.addComicToLibraryFirstTime("Bearer $token", libraryRequest)

        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    // Xử lý lỗi 400 (Bad Request)
                    Log.e("LibraryRepositoryImpl", "Bad Request")
                }

                401 -> {
                    // Xử lý lỗi 401 (Unauthorized)
                    Log.e("LibraryRepositoryImpl", "Unauthorized")
                }
                // Xử lý các mã lỗi khác
                else -> {
                    // Xử lý mặc định cho các mã lỗi khác
                }
            }
            Result.failure(Exception("Failed to add comic to library"))
        }
        return response
    }


    /**
     * TODO: Implement the function to add a comic to library category
     * in the second time they have followed the comic and want to add
     * into another category
     */
    override suspend fun addComicToLibrarySecondTime(
        token: String,
        comicId: Long,
        categoriesId: List<Long>
    ): Response<Unit> {
        val response = api.addComicToLibrarySecondTime("Bearer $token", comicId, categoriesId)
        if (response.isSuccessful) {
            Result.success(Unit)
        }
        return response
    }


    /**
     * TODO: Implement the function to remove a comic from library category
     * when they remove a comic from a category
     */
    override suspend fun removeComicFromCategory(
        token: String,
        comicId: Long,
        categoriesId: List<Long>
    ): Response<Unit> {
        val response = api.removeComicFromCategory("Bearer $token", comicId, categoriesId)
        if (response.isSuccessful) {
            Result.success(Unit)
        }
        return response
    }

    /**
     * TODO: Implement the function to remove a comic from library category
     * when they unfollow the comic or remove a comic from all category
     * and remove the comic from library
     * unFollowComic
     */
    override suspend fun unfollowComicFromLibrary(
        token: String,
        comicId: Long
    ): Response<Unit> {
        val response = api.unfollowComicFromLibrary("Bearer $token", comicId)
        if (response.isSuccessful) {
            Result.success(Unit)
        }
        return response
    }

    /**
     * TODO: Implement the function to get
     * list category which comic is in
     */
    override suspend fun getCategoriesOfComic(
        token: String,
        comicId: Long
    ): Result<LibraryEntry> {
        return try {
            val response = api.getCategoriesOfComic("Bearer $token", comicId)
            Log.d("LibraryRepositoryImpl", "getCategoriesOfComic: $response")
            Result.success(response)
        } catch (e: Exception) {
            Log.e("LibraryRepositoryImpl", "getCategoriesOfComic: $e")
            Result.failure(e)
        }
    }


    /**
     * TODO: Implement the function to get default comic that not in which category
     */
    override suspend fun getDefaultComicsInLibrary(
        token: String,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<LibraryEntry>> {
        return try {
            val response = api.getDefaultComicInLibrary("Bearer $token", orderBy, page, size)
            Log.d("LibraryRepositoryImpl", "getDefaultComicsInLibrary: $response")
            Result.success(response)
        } catch (e: Exception) {
            Log.e("LibraryRepositoryImpl", "getDefaultComicsInLibrary: $e")
            Result.failure(e)
        }
    }


}