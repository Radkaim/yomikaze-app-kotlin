package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicApiService
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest

import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val api: ComicApiService
):ComicRepository{
    override suspend fun getHotComicBannerImages(): Result<List<ComicResponseTest>> {
        return try {
            val response = api.getHotComicBannerImages()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}