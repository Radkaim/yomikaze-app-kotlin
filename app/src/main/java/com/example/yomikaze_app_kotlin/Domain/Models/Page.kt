package com.example.yomikaze_app_kotlin.Domain.Models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "pages",
    foreignKeys = [ForeignKey(
        entity = Chapter::class,
        parentColumns = ["number"],
        childColumns = ["number"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["number"]),
        Index(value = ["comicId"]) // Thêm chỉ mục cho cột comicId
    ]
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
    @SerializedName("comicId")
    //for database
    @ColumnInfo(name = "comicId")
    val comicId: Long,

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

//    //for api
//    @SerializedName("index")
//    //for database
//    @ColumnInfo(name = "pageIndex")
//    val pageIndex: Int,

    //for api
    @SerializedName("pages")
    //for database
    @ColumnInfo(name = "pages")
    val pages: List<String>,

    //for api
    @SerializedName("creationTime")
    //for database
    @ColumnInfo(name = "creationTime")
    val creationTime: String,

    //for database
    @ColumnInfo(name = "imageLocalPath")
    val imageLocalPaths: List<String>?,
)