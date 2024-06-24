package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest


interface ComicRepository {

    suspend fun getHotComicBannerImages() : Result<List<ComicResponseTest>>

}