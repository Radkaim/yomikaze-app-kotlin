package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Models.RatingRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import retrofit2.Response
import javax.inject.Inject

class RatingComicUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun ratingComic(
        token: String,
        comicId: Long,
        rating: RatingRequest
    ): Response<Unit> {
        return comicRepository.rateComic(token, comicId, rating)
    }
}