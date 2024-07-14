package com.example.yomikaze_app_kotlin.Domain.Models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "chapters",
    indices = [
        Index(value = ["number"], unique = true),
        Index(value = ["comicId"]) // Thêm chỉ mục cho cột comicId
    ],
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
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val chapterId: Long,

    //for api
    @SerializedName("comicId")
    //for database
    @ColumnInfo(name = "comicId")
    @NonNull
    val comicId: Long,

    //for api
    @SerializedName("views")
    //for database
    @ColumnInfo(name = "views")
    val views: Long,

    //for api
    @SerializedName("hasLock")
    //for database
    @ColumnInfo(name = "hasLock")
    val hasLock: Boolean,

    //for api
    @SerializedName("number")
    //for database
    @ColumnInfo(name = "number")
    @NonNull
    val number: Int,


    //for api
    @SerializedName("name")
    //for database
    @ColumnInfo(name = "name")
    val name: String,

    //for api
    @SerializedName("price")
    //for database
    @ColumnInfo(name = "price")
    val price: Int,

    //for api
    @SerializedName("isUnlocked")
    //for database
    @ColumnInfo(name = "isUnlocked")
    val isUnlocked: Boolean,


    //for api
    @SerializedName("totalComments")
    //for database
    @ColumnInfo(name = "totalComments")
    val comments: Long,

    //for api
    @SerializedName("creationTime")
    //for database
    @ColumnInfo(name = "creationTime")
    val creationTime: String,

    //for database
    @ColumnInfo(name = "size")
    var size: Long,

    //for database
    @ColumnInfo(name = "isDownloaded")
    var isDownloaded: Boolean,

    //for database
    @ColumnInfo(name = "isLocked")
    val isLocked: Boolean,

    val isSelected: Boolean = false
)
data class ContinueReadingResponse(

    @SerializedName("pageNumber")
    val pageNumber: Int,

    @SerializedName("chapterId")
    val chapterId: Long,
)