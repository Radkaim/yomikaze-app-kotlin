package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("id")
    var id: Long,

    @SerializedName("content")
    var content: String,

    @SerializedName("author")
    var author: ProfileResponse,

    @SerializedName("replies")
    val replies: List<CommentResponse>? = null,

    @SerializedName("creationTime")
    var creationTime: String,
    )
data class CommentRequest(
    @SerializedName("content")
    val content: String
)