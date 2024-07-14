package com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yomikaze_app_kotlin.Domain.Models.Page

@Dao
interface PageDao {
    @Query("SELECT * FROM pages WHERE number = :number AND comicId = :comicId")
    fun getPageByComicIdAndChapterNumberDB(comicId: Long, number: Int): Page

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(page: Page)

    @Delete
    fun deletePage(page: Page)

    @Update
    fun updateImage(page: Page)
}