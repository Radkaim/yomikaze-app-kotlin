package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class CoinPricingResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("amount")
    val amount: Long,

    @SerializedName("price")
    val price: Int,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("creationTime")
    val createdTime: String,
)
