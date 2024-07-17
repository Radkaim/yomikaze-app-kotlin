package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class PaymentSheetResponse(
    @SerializedName("clientSecret")
    val paymentIntentClientSecret: String,

    @SerializedName("customer")
    val customer: String,

    @SerializedName("ephemeralKey")
    val ephemeralKey: String,

    @SerializedName("publishableKey")
    val publishableKey: String
)

data class PaymentSheetRequest(
    @SerializedName("priceId")
    val priceId: Long
)