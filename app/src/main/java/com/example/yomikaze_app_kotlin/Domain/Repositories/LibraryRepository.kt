package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryRequest
import retrofit2.Response

interface LibraryRepository {

    /**
     * TODO: Implement the function to search comic in all category of library
     * -- search comic in library
     */
    suspend fun searchComicInLibraries(
        token: String,
        name: String,
    ): Result<BaseResponse<LibraryEntry>>


    /**
     * TODO: Implement the function to get list comic in library by category name
     * -- view category detail
     */
    suspend fun getComicsByCategoryId(
        token: String,
        category: Long,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<LibraryEntry>>

    /**
     * TODO: Implement the function to add a comic to library category
     * in the first time they follow the comic
     */
    suspend fun addComicToLibraryFirstTime(
        token: String,
        libraryRequest: LibraryRequest
    ): Response<Unit>


    /**
     * TODO: Implement the function to add a comic to library category
     * in the second time they have followed the comic and want to add
     * into another category
     */
    suspend fun addComicToLibrarySecondTime(
        token: String,
        comicId: Long,
        categoriesId: List<Long>,
    ): Response<Unit>

    /**
     * TODO: Implement the function to remove a comic from library category
     * when they remove a comic from a category
     */
    suspend fun removeComicFromCategory(
        token: String,
        comicId: Long,
        categoriesId: List<Long>
    ): Response<Unit>

    /**
     * TODO: Implement the function to remove a comic from library category
     * when they unfollow the comic or remove a comic from all category
     * and remove the comic from library
     * unFollowComic
     */
    suspend fun unfollowComicFromLibrary(
        token: String,
        comicId: Long,
    ): Response<Unit>


    /**
    * TODO: Implement the function to get
    * list category which comic is in
    */
    suspend fun getCategoriesOfComic(
        token: String,
        comicId: Long,
    ): Result<BaseResponse<LibraryEntry>>
}