package com.example.yomikaze_app_kotlin.Domain.UseCases.Comic

import com.example.yomikaze_app_kotlin.Domain.Models.ReportRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import retrofit2.Response
import javax.inject.Inject

class ReportComicUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun reportComic(
        token: String,
        comicId: Long,
        reportRequest: ReportRequest
    ): Response<Unit> {
        return comicRepository.reportComic(
            token,
            comicId,
            reportRequest
        )
    }
}