package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.CoinShop

import com.example.yomikaze_app_kotlin.Domain.Models.CoinPricingResponse
import com.example.yomikaze_app_kotlin.Domain.Models.PaymentSheetResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse

data class CoinShopState(

    val coinPricingList: List<CoinPricingResponse> = emptyList(),
    val isGetCoinPricingLoading: Boolean = false,

    val paymentSheetResponse: PaymentSheetResponse? = null,
    val isGetPaymentSheetResponseSuccess: Boolean = false,

    val profileResponse: ProfileResponse? = null,
    val isGetProfileLoading: Boolean = false,
)