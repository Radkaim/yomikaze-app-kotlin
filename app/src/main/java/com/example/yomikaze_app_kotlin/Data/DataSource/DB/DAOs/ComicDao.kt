package com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse

@Dao
interface ComicDao {
    @Query("SELECT * FROM comics")
    fun getAllComicsDownloaded(): List<ComicResponse>

    @Query("SELECT * FROM comics WHERE id = :comicId")
    fun getComicById(comicId: Long): ComicResponse

    //insert comic
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComic(comic: ComicResponse)

    // update total mbs of comic
    @Query("UPDATE comics SET totalMbs = :totalsMbs WHERE id = :comicId")
    suspend fun updateTotalMbsOfComic(comicId: Long, totalsMbs: Float)

    //delete comic
    @Delete
    suspend fun deleteComic(comic: ComicResponse)
}