package com.example.yomikaze_app_kotlin.Domain.Model

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

data class ComicResponse(

    @SerializedName("id")
    val comicId: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("aliases")
    val aliases: List<String>,

    @SerializedName("description")
    val description: String,

    @SerializedName("cover")
    val cover: String,

    @SerializedName("banner")
    val banner: String,

    @SerializedName("publicationDate")
    val publicationDate: String,

    @SerializedName("authors")
    val authors: List<String>,

    @SerializedName("status")
    val status: String,

    @SerializedName("tags")
    val tags: List<Tag>,

    @SerializedName("creationTime")
    val creationTime: String
)

data class Comic(
    val comicId: Int,
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
//data class CardComic(
//    val comicId: Int,
//    val image: String,
//    val comicName: String,
//    val authorName: String,
//    val chapter: String
//)
