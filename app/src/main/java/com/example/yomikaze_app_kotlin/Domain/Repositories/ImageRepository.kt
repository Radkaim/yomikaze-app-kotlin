package com.example.yomikaze_app_kotlin.Domain.Repositories

import android.content.Context
import com.example.yomikaze_app_kotlin.Data.RepositoriesImpl.DownloadResult

interface ImageRepository {
    suspend fun returnImageLocalPath(image: ByteArray, context: Context) : String

    suspend fun downloadImageFromApi(url: String) : DownloadResult

    // for delete image from local storage
    suspend fun deleteImageFromLocal(imagePath: String) : Boolean
}