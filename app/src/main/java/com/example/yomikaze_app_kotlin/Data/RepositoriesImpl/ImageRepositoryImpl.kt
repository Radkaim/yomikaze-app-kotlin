package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ImageApiService
import com.example.yomikaze_app_kotlin.Domain.Models.ImageResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ImageRepository
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
sealed class DownloadResult {
    data class Success(val imageData: ByteArray) : DownloadResult()
    object Failure : DownloadResult()
}
class ImageRepositoryImpl @Inject constructor(
    private val imageApiService: ImageApiService
) : ImageRepository {
    override suspend fun returnImageLocalPath(image: ByteArray, context: Context): String {
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        file.outputStream().use {
            it.write(image)
        }
        Log.d("ImageRepositoryImpl", "Saved image to: ${file.absolutePath}")
        return file.absolutePath
    }



    override suspend fun downloadImageFromApi(imageUrl: String): DownloadResult {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(imageUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 30000 // Thiết lập timeout cho kết nối (30 giây)

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return DownloadResult.Failure
            }

            val inputStream: InputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Reduce image resolution if needed
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)
            val compressedBitmap = compressBitmap(bitmap, 70) // Nén ảnh với chất lượng 80%


            if (compressedBitmap != null) {
                val outputStream = ByteArrayOutputStream()
                compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                outputStream.close()
                return DownloadResult.Success(outputStream.toByteArray())
            } else {
                return DownloadResult.Failure
            }

//            Log.d("ImageRepositoryImpl", "Downloaded image from: $imageUrl")
//            return DownloadResult.Success(outputStream.toByteArray())
        } catch (e: Exception) {
            Log.e("ImageRepositoryImpl", "Error downloading image from $imageUrl", e)
            return DownloadResult.Failure
        } finally {
            connection?.disconnect()
        }
    }

//    override suspend fun downloadImageFromApi(imageUrl: String): DownloadResult {
//        var connection: HttpURLConnection? = null
//        try {
//            val url = URL(imageUrl)
//            connection = url.openConnection() as HttpURLConnection
//            connection.connectTimeout = 30000 // Thiết lập timeout cho kết nối (30 giây)
//
//            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
//                return DownloadResult.Failure
//            }
//
//            val inputStream: InputStream = connection.inputStream
//            val outputStream = ByteArrayOutputStream()
//
//            val buffer = ByteArray(4096)
//            var bytesRead: Int
//            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//                outputStream.write(buffer, 0, bytesRead)
//            }
//            outputStream.close()
//            Log.d("ImageRepositoryImpl", "Downloaded image from: $imageUrl")
//            return DownloadResult.Success(outputStream.toByteArray())
//        } catch (e: Exception) {
//            Log.e("ImageRepositoryImpl", "Error downloading image from $imageUrl", e)
//            return DownloadResult.Failure
//        } finally {
//            connection?.disconnect()
//        }
//    }

    override suspend fun deleteImageFromLocal(imagePath: String): Boolean {
        val file = File(imagePath)
        return file.delete()
    }

    // Function to compress Bitmap
    private fun compressBitmap(bitmap: Bitmap, quality: Int): Bitmap? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val byteArray = outputStream.toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    override suspend fun uploadImage(token: String, file: MultipartBody.Part): Result<ImageResponse> {
//        val requestBody = "file".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        return try {
            val response = imageApiService.uploadImage("Bearer $token", file)
            Log.d("ImageRepositoryImpl", "Uploaded image: $response")
            Result.success(response)
        } catch (e: HttpException) {
            Log.e("ImageRepositoryImpl", "Error uploading image", e)
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("ImageRepositoryImpl", "Error uploading image: $errorBody", e)
            Result.failure(e)
        }

    }
}