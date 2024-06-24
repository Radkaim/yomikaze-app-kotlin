package com.example.yomikaze_app_kotlin.Domain.Models

data class Chapter(
    val chapterIndex: Int,
    val title: String,
    val views: Long,
    val comments: Long,
    val publishedDate: String,
    val isLocked: Boolean
)