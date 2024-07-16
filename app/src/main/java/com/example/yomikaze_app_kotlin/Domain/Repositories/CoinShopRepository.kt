package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CoinPricingResponse

interface CoinShopRepository {
    suspend fun getCoinPricing(
        token: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CoinPricingResponse>>


}