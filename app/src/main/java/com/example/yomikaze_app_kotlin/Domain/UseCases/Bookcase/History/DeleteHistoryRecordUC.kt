package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History

import com.example.yomikaze_app_kotlin.Domain.Repositories.HistoryRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteHistoryRecordUC @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend fun deleteHistoryRecord(
        token: String,
        historyRecordId: Long
    ): Response<Unit> {
        return historyRepository.deleteHistoryRecord(token, historyRecordId)
    }
}