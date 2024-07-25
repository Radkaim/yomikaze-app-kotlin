package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryEntry
import com.example.yomikaze_app_kotlin.Domain.Models.LibraryRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LibraryApiService {
    /**
     * TODO: Implement the function to search comic in all category of library
     */
    @GET("library")
    suspend fun searchComicInLibraries(
        @Header("Authorization") token: String,
        @Query("Name") name: String,
        @Query("OrderBy") nameDesc: String = "NameDesc",
        @Query("Page") page: Int = 1,
        @Query("Size") size: Int = 100,
    ): BaseResponse<LibraryEntry>


    /**
     * TODO: Implement the function to get list comic in library by category name
     * -- view category detail
     * 69059150134378496
     */
    @GET("library/category/{categoryId}")
    suspend fun getComicsByCategoryId(
        @Header("Authorization") token: String,
        @Path("categoryId") category: Long,
        @Query("OrderBy") orderBy: String? = null,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<LibraryEntry>





    /**
     * TODO: Implement the function to add a comic to library category
     * in the first time they follow the comic
     */
    @POST("library")
    suspend fun addComicToLibraryFirstTime(
        @Header("Authorization") token: String,
        @Body libraryRequest: LibraryRequest
    ): Response<Unit>

    /**
     * TODO: Implement the function to add a comic to library category
     * in the second time they have followed the comic and want to add
     * into another category
     */
    @POST("library/{comicId}/categories")
    suspend fun addComicToLibrarySecondTime(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Body categoriesId: List<Long>
    ): Response<Unit>

    /**
     * TODO: Implement the function to remove a comic from library category
     * when they remove a comic from a category
     */
    @HTTP(method = "DELETE", path = "library/{comicId}/categories", hasBody = true)
    suspend fun removeComicFromCategory(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Body categoriesId: List<Long>
    ): Response<Unit>

    /**
     * TODO: Implement the function to remove a comic from library category
     * when they unfollow the comic or remove a comic from all category
     * and remove the comic from library
     * unFollowComic
     */
    @DELETE("library/{comicId}")
    suspend fun unfollowComicFromLibrary(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
    ): Response<Unit>

    /**
     * TODO: Implement the function to get
     * list category which comic is in
     */
    @GET("library/{comicId}")
    suspend fun getCategoriesOfComic(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
    ): LibraryEntry


    /**
     * TODO: Implement the function to get default comic that not in which category
     */
    @GET("library/category/default")
    suspend fun getDefaultComicInLibrary(
        @Header("Authorization") token: String,
        @Query("OrderBy") orderBy: String? = null,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<LibraryEntry>


}