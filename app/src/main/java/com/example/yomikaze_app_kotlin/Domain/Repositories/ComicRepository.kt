package com.example.yomikaze_app_kotlin.Domain.Repositories

import android.content.Context
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.RatingRequest
import com.example.yomikaze_app_kotlin.Domain.Models.Tag
import retrofit2.Response


interface ComicRepository {

    /**
     * TODO: use for search comic in home screen
     */
    suspend fun searchComic(
        token: String,
        comicNameQuery: String,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    /**
     * TODO: use for advanced search comic in AdvancedSearch screen
     */
    suspend fun advancedSearchComic(
        token: String,
        queryMap: Map<String, String>
    ): Result<BaseResponse<ComicResponse>>


    /**
     * TODO: use for get comic in ranking weekly
     */
    suspend fun getComicWeekly(
        token: String
    ): Result<List<ComicResponse>>

    /**
     * TODO: use for get comic in ranking screen by view ranking
     */
    suspend fun getComicByViewRanking(
        token: String,
        orderByTotalViews: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    /**
     * TODO: use for get comic in ranking screen by comments ranking
     */
    suspend fun getComicByCommentsRanking(
        token: String,
        orderByTotalComments: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    /**
     * TODO: use for get comic in ranking screen by follow ranking
     */
    suspend fun getComicByFollowRanking(
        token: String,
        orderByTotalFollows: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    /**
     * TODO: use for get comic in ranking screen by rating ranking
     */
    suspend fun getComicByRatingRanking(
        token: String,
        orderByTotalRatings: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    /**
     * TODO: use for get a specific comic details by comic id
     */
    suspend fun getComicDetailsFromApi(
        token: String,
        comicId: Long,
    ): Result<ComicResponse>


    /**
     * TODO: use for rating a comic
     */
    suspend fun rateComic(
        token: String,
        comicId: Long,
        rating: RatingRequest
    ): Response<Unit>


    /**
     * ---------------------------------------------------------------------------------------------------
     * TODO: Use For DATABASE
     */

    /**
     * TODO: use for download comic and add to database
     */
    suspend fun insertComicDB(
        comic: ComicResponse,
        context: Context
    )

    /**
     * TODO: use for get all comic downloaded in database
     */
    suspend fun getAllComicsDownloadedDB(): Result<List<ComicResponse>>

    /**
     * Todo delete a comic in database by comic id and all related data
     */
    suspend fun deleteComicByIdDB(
        comicId: Long,
        context: Context
    )

    /**
     * TODO: use for get comic by comic id in database
     */
    suspend fun getComicByIdDB(
        comicId: Long
    ): ComicResponse


    /**
     * TODO: use for update total mbs of comic in database
     */
    suspend fun updateTotalMbsOfComic(
        comicId: Long,
        totalsMbs: Long
    )

    /**
     * TODO: get tags
     */
    suspend fun getTags(
        token: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<Tag>>

}