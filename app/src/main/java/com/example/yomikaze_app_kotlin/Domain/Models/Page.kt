package com.example.yomikaze_app_kotlin.Domain.Models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "pages",
    foreignKeys = [ForeignKey(
        entity = Chapter::class,
        parentColumns = ["id"],
        childColumns = ["chapterId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Page(
    //for api
    @SerializedName("id")
    //for database
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @NonNull
    val id: Long,

    //for api
    @SerializedName("chapterId")
    //for database
    @ColumnInfo(name = "chapterId")
    @NonNull
    val chapterId: Long,

    //for api
    @SerializedName("index")
    //for database
    @ColumnInfo(name = "pageIndex")
    val pageIndex: Int,

    //for api
    @SerializedName("image")
    //for database
    @ColumnInfo(name = "imageApiUrl")
    val imageApiUrl: String,

    //for database
    @ColumnInfo(name = "imageLocalPath")
    val imageLocalPath: String?,
)