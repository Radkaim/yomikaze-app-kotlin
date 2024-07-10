package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History

import com.example.yomikaze_app_kotlin.Domain.Models.PathRequest
import com.example.yomikaze_app_kotlin.Domain.Repositories.HistoryRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateLastReadPageUC @Inject constructor(
    private val historyRepository: HistoryRepository
)  {
    suspend fun updateLastReadPage(
        token: String,
        comicId: Long,
        number: Int,
        pathRequest: List<PathRequest>
    ): Response<Unit> {
        return historyRepository.updateLastReadPage(token, comicId, number, pathRequest)
    }
}