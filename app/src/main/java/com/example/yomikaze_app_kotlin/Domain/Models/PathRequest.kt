package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class PathRequest(
    @SerializedName("value")
    val value: String,

    @SerializedName("path")
    val path: String,

    @SerializedName("op")
    val op: String,

    @SerializedName("from")
    val from: String = ""

)