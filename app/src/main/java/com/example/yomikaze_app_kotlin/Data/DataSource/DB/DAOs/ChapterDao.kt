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


   // select a chapter by comicId and chapterNumber
    @Query("SELECT * FROM chapters WHERE comicId = :comicId AND number = :number")
    fun getChapterByComicIdAndChapterNumber(comicId: Long, number: Int): Chapter

    @Query("SELECT * FROM chapters WHERE id = :id")
    fun getChapterById(id: Long): Chapter

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChapter(chapter: Chapter)

    @Update
    fun updateChapterDownloaded(chapter: Chapter)

    @Delete
    fun deleteChapterDownloaded(chapter: Chapter)

    @Query("DELETE FROM chapters WHERE id = :chapterId")
    fun deleteChapterByChapterId(chapterId: Long)
}