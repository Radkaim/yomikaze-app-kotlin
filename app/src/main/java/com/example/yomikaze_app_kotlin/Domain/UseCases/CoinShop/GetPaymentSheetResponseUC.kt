package com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop

import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetRequest
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.CoinShopRepository
import javax.inject.Inject

class GetPaymentSheetResponseUC @Inject constructor(
    private val coinShopRepository: CoinShopRepository
) {
    suspend fun getPaymentSheetResponse(
        token: String,
        priceId: PaymentSheetRequest
    ): Result<PaymentSheetResponse> {
        return coinShopRepository.getPaymentSheetResponse(token, priceId)
    }
}