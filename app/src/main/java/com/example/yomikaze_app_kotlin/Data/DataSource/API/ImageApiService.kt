package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApiService {
    @Multipart
    @POST("images")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
//        @Part("ComicId") comicId: Long?= null,
//        @Part("ChapterIndex") chapterIndex: Int?= null,
//        @Part("UserId") userId: Long?= null
    ): ImageResponse // Hoặc bạn có thể thay thế Void bằng kiểu trả về mong muốn
}