package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class AdvancedSearchComicUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun executeAdvancedSearchComic(
        token: String,
        queryMap: Map<String, String>
    ): Result<BaseResponse<ComicResponse>> {
        return comicRepository.advancedSearchComic(token, queryMap)
    }
}