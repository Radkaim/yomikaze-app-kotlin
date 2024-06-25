package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("page_size")
    val pageSize: Int,
    @SerializedName("row_count")
    val rowCount: Int,
    @SerializedName("page_count")
    val pageCount: Int,
    @SerializedName("results")
    val results: List<T>
)
