package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponseTest
import retrofit2.http.GET


//Data class for the response from the API
// inherit from comic class



interface ComicApiService {

    @GET("photos")
    suspend fun getHotComicBannerImages(): List<ComicResponseTest>
}