package com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetComicByRatingRankingUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun getComicByRatingRanking(
        token: String,
        orderByAverageRatings: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>> {
        return comicRepository.getComicByRatingRanking(token, orderByAverageRatings, page, size)
    }
}