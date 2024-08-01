package com.example.yomikaze_app_kotlin.Domain.UseCases.Profile

import com.example.yomikaze_app_kotlin.Domain.Models.ImageResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadImageUC @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend fun uploadImage(token: String, file: MultipartBody.Part): Result<ImageResponse>
    {
        return imageRepository.uploadImage(token, file)
    }
}