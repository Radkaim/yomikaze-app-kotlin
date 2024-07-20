package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class LibraryEntry(
    @SerializedName("comic")
    val libraryEntry: ComicResponse,
)

data class LibraryRequest(
    @SerializedName("comicId")
    val comicId :Long,

    @SerializedName("categoryIds")
    val categoryIds: List<Long>
)