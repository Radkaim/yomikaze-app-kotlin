package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("totals")
    val totals: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("results")
    val results: List<T>
)
