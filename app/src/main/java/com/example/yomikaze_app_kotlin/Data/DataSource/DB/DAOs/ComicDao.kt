package com.example.yomikaze_app_kotlin.Data.DataSource.DB.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.yomikaze_app_kotlin.Domain.Model.ComicResponse

@Dao
interface ComicDao {
    @Query("SELECT * FROM comics")
    fun getAllComics(): List<ComicResponse>

    @Query("SELECT * FROM comics WHERE id = :comicId")
    fun getComicById(comicId: Long): ComicResponse

    //add comic
    @Insert
    suspend fun addComic(comic: ComicResponse)

   // update total mbs of comic
    @Query("UPDATE comics SET totalMbs = :totalsMbs WHERE id = :comicId")
    suspend fun updateTotalMbsOfComic(comicId: Long, totalsMbs: Float)

    //delete comic
    @Delete
    suspend fun deleteComic(comic: ComicResponse)
}