package com.example.yomikaze_app_kotlin.Domain.UseCases

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Tag
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetTagsUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun getTags(
        token: String,
        page: Int? = null,
        size: Int? = null
        ): Result<BaseResponse<Tag>> {
        return comicRepository.getTags(token, page, size)
    }
}