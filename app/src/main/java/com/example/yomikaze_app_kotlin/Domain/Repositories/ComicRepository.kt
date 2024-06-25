package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest


interface ComicRepository {

    suspend fun getHotComicBannerImages() : Result<List<ComicResponseTest>>

    suspend fun searchComic(token: String, comicNameQuery: String) : Result<BaseResponse<ComicResponse>>
}