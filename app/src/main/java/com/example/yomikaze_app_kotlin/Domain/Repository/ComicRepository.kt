package com.example.yomikaze_app_kotlin.Domain.Repository

import com.example.yomikaze_app_kotlin.Data.DataSource.API.ComicResponseTest

interface ComicRepository {

    suspend fun getHotComicBannerImages() : Result<List<ComicResponseTest>>

}