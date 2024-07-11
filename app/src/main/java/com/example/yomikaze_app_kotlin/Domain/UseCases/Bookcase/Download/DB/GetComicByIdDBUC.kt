package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download.DB

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class GetComicByIdDBUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun getComicByIdDB(comicId: Long): ComicResponse {
        return comicRepository.getComicByIdDB(comicId)
    }
}