package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.RatingRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReportRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ReportResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Tag
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


//Data class for the response from the API
// inherit from comic class
interface ComicApiService {

    /**
     * TODO: use for search comic in home screen
     */
    @GET("comics")
    suspend fun searchComicByName(
        @Header("Authorization") token: String,
        @Query("Name") name: String,
        @Query("Size") size: Int? = null,
        @Query("OrderBy") orderByTotalViews: String? = "TotalViews",
        @Query("OrderBy") orderByName: String? = "Name",
    ): BaseResponse<ComicResponse>

    /**
     * TODO: use for advanced search comic in AdvancedSearch screen
     */

    @GET("comics")
    suspend fun advancedSearchComic(
        @Header("Authorization") token: String,
        @QueryMap queryMap: Map<String, String>,
        @Query("Page") page: Int = 1,
        @Query("Size") size: Int = 500,
    ): BaseResponse<ComicResponse>


    /**
     * TODO: use for get comic in ranking weekly
     */
    @GET("comics/ranking/weekly")
    suspend fun getComicWeekly(
        @Header("Authorization") token: String,
    ): List<ComicResponse>


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
        @Query("Size") size: Int? = null,
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


    /**
     * TODO: get tag
     */
    @GET("tags")
    suspend fun getTags(
        @Header("Authorization") token: String,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<Tag>


    /**
     * TODO: get common report reasons of comic
     */
    @GET("reports/comic/reasons")
    suspend fun getComicCommonReportReasons(
    ): List<ReportResponse>

    /**
     * TODO: use for report a comic
     */
    @POST("reports/comic/{comicId}")
    suspend fun reportComic(
        @Header("Authorization") token: String,
        @Path("comicId") comicId: Long,
        @Body reportRequest: ReportRequest
    ): Response<Unit>


}