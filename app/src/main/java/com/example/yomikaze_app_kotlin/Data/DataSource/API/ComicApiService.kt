package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest
import com.example.yomikaze_app_kotlin.Domain.Models.RatingRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


//Data class for the response from the API
// inherit from comic class
interface ComicApiService {
    @GET("photos")
    suspend fun getHotComicBannerImages(): List<ComicResponseTest>


    /**
     * TODO: use for search comic in home screen
     */
    @GET("comics")
    suspend fun searchComicByName(
        @Header("Authorization") token: String,
        @Query("Name") name: String,
        @Query("OrderBy") orderByTotalViews: String = "TotalViews",
        @Query("OrderBy") orderByName: String = "Name",
    ): BaseResponse<ComicResponse>

    /**
     * TODO: use for get comic in ranking screen by view ranking
     */
    //for hot ranking
    @GET("comics")
    suspend fun getComicByViewRanking(
        @Header("Authorization") token: String,
        @Query("OrderBy") orderByTotalViews: String,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<ComicResponse>

    /**
     * TODO: use for get comic in ranking screen by comments ranking
     */
    @GET("comics")
    suspend fun getComicByCommentsRanking(
        @Header("Authorization") token: String,
        @Query("OrderBy") orderByTotalComments: String,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<ComicResponse>

    /**
     * TODO: use for get comic in ranking screen by follow ranking
     */
    @GET("comics")
    suspend fun getComicByFollowRanking(
        @Header("Authorization") token: String,
        @Query("OrderBy") orderByTotalFollows: String,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<ComicResponse>

    /**
     * TODO: use for get comic in ranking screen by rating ranking
     */
    @GET("comics")
    suspend fun getComicByRatingRanking(
        @Header("Authorization") token: String,
        @Query("OrderBy") orderByAverageRatings: String,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int?,
    ): BaseResponse<ComicResponse>

    /**
     * TODO: use for get a specific comic details by comic id
     */
    @GET("comics/{comicId}")
    suspend fun getComicDetailsFromAPI(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
    ): ComicResponse

    /**
     * TODO: use for rating a comic
     */
    @PUT("comics/{key}/rate")
    suspend fun rateComic(
        @Header("Authorization") token: String,
        @Path("key") comicId: Long,
        @Body body: RatingRequest
    ): Response<Unit>
}