package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.CoinShopApiService
import com.example.yomikaze_app_kotlin.Domain.Models.BaseResponse
import com.example.yomikaze_app_kotlin.Domain.Models.CoinPricingResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetRequest
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetResponse
import com.example.yomikaze_app_kotlin.Domain.Models.TransactionHistoryResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.CoinShopRepository
import javax.inject.Inject

class CoinShopRepositoryImpl @Inject constructor(
    private val api: CoinShopApiService
) : CoinShopRepository {
    override suspend fun getCoinPricing(
        token: String,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<CoinPricingResponse>> {
        return try {
            val response = api.getCoinPricing(token, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Log.e("CoinShopRepositoryImpl", "getCoinPricing: $e")
            Result.failure(e)
        }
    }

    override suspend fun getPaymentSheetResponse(
        token: String,
        priceId: PaymentSheetRequest
    ): Result<PaymentSheetResponse> {
        return try {
            val response = api.getPaymentSheetResponse("Bearer $token", priceId)
            Result.success(response)
        } catch (e: Exception) {
            Log.e("CoinShopRepositoryImpl", "getPaymentSheetResponse: $e")
            Result.failure(e)
        }
    }

    /**
     * TODO: Get history transaction of user
     */
    override suspend fun getCoinTransaction(
        token: String,
        orderBy: String?,
        page: Int?,
        size: Int?
    ): Result<BaseResponse<TransactionHistoryResponse>> {
        return try {
            val response = api.getCoinTransaction("Bearer $token", orderBy, page, size)
            Result.success(response)
        } catch (e: Exception) {
            Log.e("CoinShopRepositoryImpl", "getCoinTransaction: $e")
            Result.failure(e)
        }
    }
}