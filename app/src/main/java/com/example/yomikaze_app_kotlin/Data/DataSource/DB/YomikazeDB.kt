package com.example.yomikaze_app_kotlin.Data.DataSource.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs.ComicDao
import com.example.yomikaze_app_kotlin.Data.DataSource.DB.Helpers.Converters
import com.example.yomikaze_app_kotlin.Domain.Model.ComicResponse

@Database(entities = [ComicResponse::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class YomikazeDB : RoomDatabase(){
    abstract fun comicDao(): ComicDao
}