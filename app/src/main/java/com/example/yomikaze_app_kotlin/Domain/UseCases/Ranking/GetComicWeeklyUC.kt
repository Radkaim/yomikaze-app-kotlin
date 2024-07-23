package com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetComicWeeklyUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun getComicWeekly(token: String): Result<List<ComicResponse>> {
        return comicRepository.getComicWeekly(token)
    }
}