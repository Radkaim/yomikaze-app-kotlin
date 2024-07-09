package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class LibraryCategoryRequest(
    @SerializedName("name")
    val name: String
)

data class LibraryCategoryResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("creationTime")
    val creationTime: String,

    var firstCoverImage: String?,

    var totalComics: Int?
)