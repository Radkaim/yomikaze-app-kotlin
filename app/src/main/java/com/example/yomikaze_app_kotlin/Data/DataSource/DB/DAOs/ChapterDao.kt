package com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter

@Dao
interface ChapterDao {

    @Query("SELECT * FROM chapters WHERE comicId = :comicId")
     fun getListChaptersDownloadedByComicId(comicId: Long): List<Chapter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertChapter(chapter: Chapter)

    @Update
     fun updateChapterDownloaded(chapter: Chapter)

    @Delete
     fun deleteChapterDownloaded(chapter: Chapter)
}