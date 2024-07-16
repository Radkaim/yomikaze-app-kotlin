package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.CoinShop

import com.example.yomikaze_app_kotlin.Domain.Models.CoinPricingResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetResponse

data class CoinShopState(

    val coinPricingList: List<CoinPricingResponse> = emptyList(),
    val isGetCoinPricingLoading: Boolean = false,

    val paymentSheetResponse: PaymentSheetResponse? = null,
    val isGetPaymentSheetResponseSuccess: Boolean = false
)