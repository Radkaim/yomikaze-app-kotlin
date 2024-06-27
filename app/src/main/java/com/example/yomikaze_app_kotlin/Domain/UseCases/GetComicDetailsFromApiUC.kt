package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetComicDetailsFromApiUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun getComicDetailsFromApi(
        token: String,
        comicId: Long
    ): Result<ComicResponse> {
        return comicRepository.getComicDetailsFromApi(token, comicId)
    }
}