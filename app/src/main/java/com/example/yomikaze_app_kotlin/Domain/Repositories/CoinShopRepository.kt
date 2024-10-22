package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CoinPricingResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetRequest
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetResponse
import com.example.yomikaze_app_kotlin.Domain.Models.TransactionHistoryResponse

interface CoinShopRepository {
    suspend fun getCoinPricing(
        token: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CoinPricingResponse>>


    suspend fun getPaymentSheetResponse(
        token: String,
        priceId: PaymentSheetRequest
    ): Result<PaymentSheetResponse>

    /**
     * TODO: Get history transaction of user
     */
    suspend fun getCoinTransaction(
        token: String,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<TransactionHistoryResponse>>
}