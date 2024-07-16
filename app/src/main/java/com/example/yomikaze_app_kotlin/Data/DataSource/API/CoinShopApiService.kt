package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CoinPricingResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CoinShopApiService {

    /**
     * TODO: use for get coin-pricing in CoinShop screen
     */
    @GET("coin-pricing")
    suspend fun getCoinPricing(
        @Header("Authorization") token: String,
        @Query("Page") page: Int? = null,
        @Query("Size") size: Int? = null,
    ): BaseResponse<CoinPricingResponse>

}