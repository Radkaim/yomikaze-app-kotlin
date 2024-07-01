package com.example.yomikaze_app_kotlin.Domain.Repositories

import android.content.Context
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest
import com.example.yomikaze_app_kotlin.Domain.Models.RatingRequest
import retrofit2.Response


interface ComicRepository {

    suspend fun getHotComicBannerImages(): Result<List<ComicResponseTest>>

    /**
     * TODO: use for search comic in home screen
     */
    suspend fun searchComic(
        token: String,
        comicNameQuery: String
    ): Result<BaseResponse<ComicResponse>>

    /**
     * TODO: use for get comic in ranking screen by view ranking
     */
    suspend fun getComicByViewRanking(
        token: String,
        orderByTotalViews: String,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    /**
     * TODO: use for get comic in ranking screen by comments ranking
     */
    suspend fun getComicByCommentsRanking(
        token: String,
        orderByTotalComments: String,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    /**
     * TODO: use for get comic in ranking screen by follow ranking
     */
    suspend fun getComicByFollowRanking(
        token: String,
        orderByTotalFollows: String,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    /**
     * TODO: use for get comic in ranking screen by rating ranking
     */
    suspend fun getComicByRatingRanking(
        token: String,
        orderByTotalRatings: String,
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
     * TODO: use for rating a comic
     *
     */
    suspend fun rateComic(
        token: String,
        comicId: Long,
        rating: RatingRequest
    ): Response<Unit>
}