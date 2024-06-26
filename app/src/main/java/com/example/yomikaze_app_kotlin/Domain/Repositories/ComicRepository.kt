package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest


interface ComicRepository {

    suspend fun getHotComicBannerImages(): Result<List<ComicResponseTest>>

    suspend fun searchComic(
        token: String,
        comicNameQuery: String
    ): Result<BaseResponse<ComicResponse>>

    suspend fun getComicByViewRanking(
        token: String,
        orderByTotalViews: String,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    suspend fun getComicByCommentsRanking(
        token: String,
        orderByTotalComments: String,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    suspend fun getComicByFollowRanking(
        token: String,
        orderByTotalFollows: String,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>

    suspend fun getComicByRatingRanking(
        token: String,
        orderByTotalRatings: String,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>>
}