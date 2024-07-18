package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("content")
    val content: String,

    @SerializedName("author")
    val author: ProfileResponse,

    @SerializedName("replies")
    val replies: List<CommentResponse>? = null,

    @SerializedName("creationTime")
    val creationTime: String,
    )
data class CommentRequest(
    @SerializedName("content")
    val content: String
)