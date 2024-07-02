package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @SerializedName("results")
    val categories: List<Category>
)

data class Category(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String
)