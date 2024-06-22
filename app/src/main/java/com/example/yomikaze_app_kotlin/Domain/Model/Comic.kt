package com.example.yomikaze_app_kotlin.Domain.Model

import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.Helpers.Converters
import com.google.gson.annotations.SerializedName

data class ComicResponseTest(
    @SerializedName("albumId")
    val albumId : Int,

    @SerializedName("id")
    val id : Int,

    @SerializedName("title")
    val title : String,

    @SerializedName("url")
    val url : String,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl : String,
)
@Entity(tableName = "comics")
data class ComicResponse(

    //for api
    @SuppressLint("KotlinNullnessAnnotation")
    @SerializedName("id")
    //for database
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    val comicId: Long,

    //for api
    @SerializedName("name")
    //for database
    @ColumnInfo(name = "name")
    val name: String,

    //for api
    @SerializedName("aliases")
    //for database
    @ColumnInfo(name = "aliases")
    @TypeConverters(Converters::class) // apply TypeConverter to aliases
    val aliases: List<String>,

    //for api
    @SerializedName("description")
    //for database
    @ColumnInfo(name = "description")
    val description: String,

    //for api
    @SerializedName("cover")
    //for database
    @ColumnInfo(name = "cover")
    val cover: String,

    //for api
    @SerializedName("banner")
    //for database
    @ColumnInfo(name = "banner")
    val banner: String,

    //for api
    @SerializedName("publicationDate")
    //for database
    @ColumnInfo(name = "publicationDate")
    val publicationDate: String,

    //for api
    @SerializedName("authors")
    //for database
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "authors")
    val authors: List<String> ,

    //for api
    @SerializedName("status")
    //for database
    @ColumnInfo(name = "status")
    val status: String,

//    //for api
//    @SerializedName("tags")
//    //for database
//    @ColumnInfo(name = "tags")
//    val tags: List<Tag> = emptyList(),

    //for api
    @SerializedName("rating")
    //for database
    @ColumnInfo(name = "rating")
    val rating: Float,

    //for api
    @SerializedName("views")
    //for database
    @ColumnInfo(name = "views")
    val views: Long,

    //for api
    @SerializedName("follows")
    //for database
    @ColumnInfo(name = "follows")
    val follows: Long,

    //for api
    @SerializedName("comments")
    //for database
    @ColumnInfo(name = "comments")
    val comments: Long,

    //for api
    @SerializedName("creationTime")
    //for database
    @ColumnInfo(name = "creationTime")
    val creationTime: String,

    //for database
    @ColumnInfo(name = "totalMbs")
    val totalMbs : Float
)

data class Comic(
    val comicId: Long,
    val rankingNumber: Int,
    val image: String,
    val comicName: String,
    val status: String,
    val authorName: String,
    val publishedDate: String,
    val ratingScore: Float,
    val follows: Long,
    val views: Long,
    val comments: Long
)
data class ComicChapter(
    val chapterId: Int,
    val rankingNumber: Int,
    val image: String,
    val comicName: String,
    val status: String,
    val authorName: String,
    val publishedDate: String,
    val ratingScore: Float,
    val follows: Int,
    val views: Int,
    val comments: Int
)

