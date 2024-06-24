package com.example.yomikaze_app_kotlin.Data.DataSource.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ChapterDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ComicDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.PageDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.Helpers.Converters
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.Page

@Database(
    entities = [ComicResponse::class, Chapter::class, Page::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class YomikazeDB : RoomDatabase() {
    abstract fun comicDao(): ComicDao
    abstract fun chapterDao(): ChapterDao
    abstract fun pageDao(): PageDao
}