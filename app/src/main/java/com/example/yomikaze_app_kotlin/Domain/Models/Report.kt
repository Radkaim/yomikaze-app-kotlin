package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class ReportResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("content")
    val content: String,

    @SerializedName("creationTime")
    val creationTime: String,
)

data class ReportRequest(
    @SerializedName("reasonId")
    val reasonId: Long,

    @SerializedName("description")
    val description: String?,
)