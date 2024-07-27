package com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.TransactionHistoryResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.CoinShopRepository
import javax.inject.Inject

class GetTransactionHistoryUC @Inject constructor(
    private val coinShopRepository: CoinShopRepository
) {
    suspend fun getTransactionHistory(
        token: String,
        orderBy: String? = null,
        page: Int? = null,
        size: Int? = null,
    ): Result<BaseResponse<TransactionHistoryResponse>> {
        return coinShopRepository.getCoinTransaction(token, orderBy, page, size)
    }

}