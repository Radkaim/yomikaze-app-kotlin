package com.example.yomikaze_app_kotlin.Domain.UseCases.Bookcase.Download

import android.content.Context
import com.example.yomikaze_app_kotlin.Domain.Repositories.ComicRepository
import javax.inject.Inject

class DeleteComicByIdDBUC @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend fun deleteComicByIdDB(comicId: Long, context: Context){
        comicRepository.deleteComicByIdDB(comicId, context)
    }

}