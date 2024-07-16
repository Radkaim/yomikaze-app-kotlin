package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("author")
    val author: Author,

    @SerializedName("replies")
    val replies: List<CommentResponse>? = null,

    @SerializedName("creationTime")
    val creationTime: String,

)
