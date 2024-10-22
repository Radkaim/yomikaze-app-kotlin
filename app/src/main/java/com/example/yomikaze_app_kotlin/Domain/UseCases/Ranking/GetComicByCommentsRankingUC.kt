package com.example.yomikaze_app_kotlin.Domain.UseCases.Ranking

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetComicByCommentsRankingUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun getComicByCommentsRanking(
        token: String,
        orderByTotalComments: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>> {
        return comicRepository.getComicByCommentsRanking(token, orderByTotalComments, page, size)
    }
}