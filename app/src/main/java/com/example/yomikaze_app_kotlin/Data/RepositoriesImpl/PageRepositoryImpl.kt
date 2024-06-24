package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.content.Context
import com.example.yomikaze_app_kotlin.Domain.Models.Page
import com.example.yomikaze_app_kotlin.Domain.Repositories.PageRepository
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class PageRepositoryImpl @Inject constructor(

) : PageRepository {

    override suspend fun getPagesByChapterIndex(chapterIndex: Int): Result<List<Page>> {
        return Result.success(listOf())
    }

    override suspend fun saveImageToLocal(image: ByteArray, context: Context): String {
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        file.outputStream().use {
            it.write(image)
        }
        return file.absolutePath
    }

    override suspend fun downloadImage(imageUrl: String): ByteArray {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 30000 // Thiết lập timeout cho kết nối (30 giây)
        try {
            val inputStream: InputStream = connection.inputStream
            val outputStream = ByteArrayOutputStream()

            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.close()

            return outputStream.toByteArray()
        } finally {
            connection.disconnect()
        }
    }
}