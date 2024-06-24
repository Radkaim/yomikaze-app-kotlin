package com.example.yomikaze_app_kotlin.Domain.Repositories

import android.content.Context
import com.example.yomikaze_app_kotlin.Domain.Models.Page

interface PageRepository {

    suspend fun getPagesByChapterIndex(chapterIndex: Int) : Result<List<Page>>
    suspend fun saveImageToLocal(image: ByteArray, context: Context) : String

    suspend fun downloadImage(url: String) : ByteArray
}