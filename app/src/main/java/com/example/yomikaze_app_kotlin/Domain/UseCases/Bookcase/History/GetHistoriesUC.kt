package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.History

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.HistoryResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.HistoryRepository
import javax.inject.Inject

class GetHistoriesUC @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend fun getHistories(
        token: String,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null
    ): Result<BaseResponse<HistoryResponse>> {
        return historyRepository.getHistories(token, orderBy, page, size)
    }
}