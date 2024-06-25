package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


//Data class for the response from the API
// inherit from comic class



interface ComicApiService {

    @GET("photos")
    suspend fun getHotComicBannerImages(): List<ComicResponseTest>

    @GET("comics")
    suspend fun getAllComics(): List<ComicResponse>

    @GET("comics/search")
    suspend fun searchComicByName(
        @Header("Authorization") token: String,
        @Query("Name") name: String,
    ): BaseResponse<ComicResponse>
}