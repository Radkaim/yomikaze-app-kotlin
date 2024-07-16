package com.example.yomikaze_app_kotlin.Domain.UseCases.CoinShop

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CoinPricingResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.CoinShopRepository
import javax.inject.Inject

class GetCoinPricingUC @Inject constructor(
    private val coinShopRepository: CoinShopRepository
) {
    suspend fun getCoinPricing(
        token: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CoinPricingResponse>> {
        return coinShopRepository.getCoinPricing(token, page, size)
    }
}

