package com.example.yomikaze_app_kotlin.Domain.Model

data class Comic(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String,
    val images: List<String>,
    val author: String

)
