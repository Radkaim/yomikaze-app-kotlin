package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetComicByRolePublisherUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend  fun getComicByRolePublisher(
        token: String,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<ComicResponse>> {
        return comicRepository.getComicByRolePublisher(token, page, size)
    }
}