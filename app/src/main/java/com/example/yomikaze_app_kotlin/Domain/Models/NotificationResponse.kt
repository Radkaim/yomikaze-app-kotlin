package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("title")
    val title: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("content")
    val content: String,

    @SerializedName("read")
    val read: Boolean,

    @SerializedName("creationTime")
    val creationTime: String,

)

data class FcmToken(
    @SerializedName("fcmToken")
    val fcmToken: String
)