package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetComicByViewRankingUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun getComicByViewRanking(
        token: String,
        orderByTotalViews: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>> {
        return comicRepository.getComicByViewRanking(token,orderByTotalViews, page, size)
    }
}