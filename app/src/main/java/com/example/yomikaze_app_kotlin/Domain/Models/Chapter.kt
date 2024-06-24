package com.example.yomikaze_app_kotlin.Domain.Models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "chapters",
    foreignKeys = [ForeignKey(
        entity = ComicResponse::class,
        parentColumns = ["id"],
        childColumns = ["comicId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Chapter(
    //for api
    @SerializedName("id")
    //for database
    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val chapterId: Long,

    //for api
    @SerializedName("ComicId")
    //for database
    @ColumnInfo(name = "comicId")
    val comicId: Long,

    //for api
    @SerializedName("index")
    //for database
    @ColumnInfo(name = "chapterIndex")
    @NonNull
    val chapterIndex: Int,

    //for api
    @SerializedName("title")
    //for database
    @ColumnInfo(name = "title")
    val title: String,

    //for api
    @SerializedName("totalViews")
    //for database
    @ColumnInfo(name = "totalViews")
    val totalViews: Long,

    //for api
    @SerializedName("totalComments")
    //for database
    @ColumnInfo(name = "totalComments")
    val totalComments: Long,

    //for database
    @ColumnInfo(name = "size")
    var size: Long,

    //for database
    @ColumnInfo(name = "isDownloaded")
    var isDownloaded: Boolean,

    //for database
    @ColumnInfo(name = "isLocked")
    val isLocked: Boolean
) : BaseModel()