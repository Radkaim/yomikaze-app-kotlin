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

data class TransactionHistoryResponse(

    @SerializedName("userId")
    val userId: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("amount")
    val amount: Long,

    @SerializedName("description")
    val description: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("creationTime")
    val createdTime: String,
)
