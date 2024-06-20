package com.example.yomikaze_app_kotlin.Domain.Model

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("creationTime")
    val creationTime: String

)