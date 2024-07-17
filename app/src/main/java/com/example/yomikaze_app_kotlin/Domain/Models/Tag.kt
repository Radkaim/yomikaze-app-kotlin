package com.example.yomikaze_app_kotlin.Domain.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tags")
data class Tag(
    //for api
    @SerializedName("id")
    //for database
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val tagId: Long,

    //for api
    @SerializedName("name")
    //for database
    @ColumnInfo(name = "name")
    val name: String,

    //for api
    @SerializedName("description")
    //for database
    @ColumnInfo(name = "description")
    val description: String,

//    //for api
//    @SerializedName("category")
//    //for database
//    @ColumnInfo(name = "category")
//    val category: String? = null,

    //for api
    @SerializedName("creationTime")
    //for database
    @ColumnInfo(name = "creationTime")
    val creationTime: String,

    // For API
    @SerializedName("category")

    val category: Category

)

data class Category(
    // For API
    @SerializedName("name")
    // For database
    @ColumnInfo(name = "name")
    val name: String,

    // For API
    @SerializedName("id")
    // For database
    @ColumnInfo(name = "id")
    val categoryId: Long,

    // For API
    @SerializedName("creationTime")
    // For database
    @ColumnInfo(name = "creationTime")
    val creationTime: String
)