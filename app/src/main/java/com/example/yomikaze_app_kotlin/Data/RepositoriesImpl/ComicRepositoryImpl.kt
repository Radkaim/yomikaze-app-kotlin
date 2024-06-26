package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest

import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val api: ComicApiService
) : ComicRepository {
    override suspend fun getHotComicBannerImages(): Result<List<ComicResponseTest>> {
        return try {
            val response = api.getHotComicBannerImages()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchComic(
        token: String,
        comicNameQuery: String,
    ): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.searchComicByName("Bearer $token", comicNameQuery)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getComicByViewRanking(token: String): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicByViewRanking("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getComicByCommentsRanking(token: String): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicByCommentsRanking("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getComicByFollowRanking(token: String): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicByFollowRanking("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getComicByRatingRanking(token: String): Result<BaseResponse<ComicResponse>> {
        return try {
            val response = api.getComicByRatingRanking("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}