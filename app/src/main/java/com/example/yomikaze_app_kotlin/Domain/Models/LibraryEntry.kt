package com.example.yomikaze_app_kotlin.Domain.Models

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.Helpers.Converters
import com.google.gson.annotations.SerializedName

data class LibraryEntry(
    @SerializedName("comic")
    val libraryEntry: ComicResponse,

    //categories
    @SerializedName("categories")
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "categories")
    val categories: List<LibraryCategoryResponse>? = null,
)

data class LibraryRequest(
    @SerializedName("comicId")
    val comicId :Long,

    @SerializedName("categoryIds")
    val categoryIds: List<Long>
)