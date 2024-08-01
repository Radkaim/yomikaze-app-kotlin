package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class ImageResponse(
  @SerializedName("images")
  val images: List<String>
)