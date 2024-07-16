package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CoinPricingResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetRequest
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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

    /**
     * TODO: use for get payment-sheet in CoinShop screen
     */
    @POST("stripe/payment-sheet")
    suspend fun getPaymentSheetResponse(
        @Header("Authorization") token: String,
        @Body priceId: PaymentSheetRequest,
    ): PaymentSheetResponse
}