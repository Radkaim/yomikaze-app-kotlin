package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET


//Data class for the response from the API
// inherit from comic class
data class ComicResponseTest(
    @SerializedName("albumId")
    val albumId : Int,

    @SerializedName("id")
    val id : Int,

    @SerializedName("title")
    val title : String,

    @SerializedName("url")
    val url : String,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl : String,
)



interface ComicApiService {

    @GET("photos")
    suspend fun getHotComicBannerImages(): List<ComicResponseTest>
}