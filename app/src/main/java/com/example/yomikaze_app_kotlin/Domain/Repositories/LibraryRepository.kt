package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry

interface LibraryRepository {

    /**
     * TODO: Implement the function to search comic in all category of library
     */
    suspend fun searchComicInLibraries(
        token: String,
        name: String,
    ): Result<BaseResponse<LibraryEntry>>


    /**
     * TODO: Implement the function to get list comic in library by category name
     * -- view category detail
     */
    suspend fun getComicsByCategoryName(
        token: String,
        category: String,
    ): Result<BaseResponse<ComicResponse>>
}