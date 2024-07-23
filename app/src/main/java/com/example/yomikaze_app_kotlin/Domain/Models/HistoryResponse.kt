package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("chapterId")
    val chapterId: Long,

    @SerializedName("comicId")
    val comicId: Long,

    @SerializedName("pageNumber")
    val pageNumber: Int,

    @SerializedName("chapter")
    val chapter: Chapter,

    @SerializedName("comic")
    val comic: ComicResponse,

    @SerializedName("creationTime")
    val creationTime: String
)