package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


//Data class for the response from the API
// inherit from comic class
interface ComicApiService {
    @GET("photos")
    suspend fun getHotComicBannerImages(): List<ComicResponseTest>

    @GET("comics")
    suspend fun getAllComics(): List<ComicResponse>

    //use for search comic by name at home screen
    @GET("comics")
    suspend fun searchComicByName(
        @Header("Authorization") token: String,
        @Query("Name") name: String,
        @Query("OrderBy") orderByTotalViews: String = "TotalViews",
        @Query("OrderBy") orderByName: String = "Name",
    ): BaseResponse<ComicResponse>

    /**
     * TODO: use for get comic in ranking screen
     */

    //for hot ranking
    @GET("comics")
    suspend fun getComicByViewRanking(
        @Header("Authorization") token: String,
        @Query("OrderBy") orderByTotalViews: String,
        @Query("Size") size: Int? = null,
    ): BaseResponse<ComicResponse>

    //for comments ranking
    @GET("comics")
    suspend fun getComicByCommentsRanking(
        @Header("Authorization") token: String,
        @Query("OrderBy") orderByTotalComments: String,
        @Query("Size") size: Int? = null,
    ): BaseResponse<ComicResponse>

    //for follow ranking
    @GET("comics")
    suspend fun getComicByFollowRanking(
        @Header("Authorization") token: String,
        @Query("OrderBy") orderByTotalFollows: String,
        @Query("Size") size: Int? = null,
    ): BaseResponse<ComicResponse>

    //for rating ranking
    @GET("comics")
    suspend fun getComicByRatingRanking(
        @Header("Authorization") token: String,
        @Query("OrderBy") orderByAverageRatings: String,
        @Query("Size") size: Int?,
    ): BaseResponse<ComicResponse>
}