package com.example.yomikaze_app_kotlin.Domain.Repositories

import android.content.Context
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.DownloadResult
import com.example.yomikaze_app_kotlin.Domain.Models.ImageResponse
import okhttp3.MultipartBody

interface ImageRepository {
    suspend fun returnImageLocalPath(image: ByteArray, context: Context) : String

    suspend fun downloadImageFromApi(url: String) : DownloadResult

    // for delete image from local storage
    suspend fun deleteImageFromLocal(imagePath: String) : Boolean

    // for upload image to server
    suspend fun uploadImage(
        token: String,
        file: MultipartBody.Part
    ): Result<ImageResponse>

}