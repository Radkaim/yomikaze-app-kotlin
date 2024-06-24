package com.example.yomikaze_app_kotlin.Domain.Models

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

open class BaseModel {

    //for api
    @SerializedName("creationDate")
    //for database
    @ColumnInfo(name = "creationDate")
    var creationDate: String = ""

    //for api
    @SerializedName("lastModified")
    //for database
    @ColumnInfo(name = "lastModified")
    var lastModified: String = ""
}