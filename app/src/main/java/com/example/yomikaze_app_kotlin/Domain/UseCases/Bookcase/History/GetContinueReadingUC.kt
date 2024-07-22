package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History

import com.example.yomikaze_app_kotlin.Domain.Models.ContinueReadingResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.HistoryRepository
import javax.inject.Inject

class GetContinueReadingUC @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend fun getContinueReading(token: String, comicId: Long): Result<ContinueReadingResponse> {
        return historyRepository.continueReading(token, comicId)
    }
}