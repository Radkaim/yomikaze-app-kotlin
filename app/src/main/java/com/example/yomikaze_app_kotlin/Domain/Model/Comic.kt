package com.example.yomikaze_app_kotlin.Domain.Model

data class Comic(
    val comicId: Int,
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
