package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Data.DataSource.API.NotificationApiService
import com.example.yomikaze_app_kotlin.Domain.Models.NotificationResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.NotificationRepository
import retrofit2.Response
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: NotificationApiService
) : NotificationRepository {

    override suspend fun getNotifications(token: String): Result<List<NotificationResponse>> {
        return try {
            val response = api.getNotifications("Bearer $token")
//            Log.d("LibraryRepositoryImpl", "searchComicInLibraries: $response")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    //subscribe to notification
    override suspend fun subscribeToNotification(token: String, fcmToken: String):Response<Unit> {
        val response = api.subscribeToNotification("Bearer $token", fcmToken)
        if (response.isSuccessful) {
            Log.d("NotificationRepositoryImpl", "subscribeToNotification: ${response.code()}")
            Result.success(response)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    throw Exception("Bad Request")
                }
                401 -> {
                    throw Exception("Unauthorized")
                }
                403 -> {
                    throw Exception("Forbidden")
                }
                404 -> {
                    throw Exception("Not Found")
                }
                500 -> {
                    throw Exception("Internal Server Error")
                }
                else -> {
                    throw Exception("Unknown Error")
                }
            }
            Log.d("NotificationRepositoryImpl", "unsubscribeToNotification: $response")
            Result.failure(Exception("Failed to subscribe to notification"))
        }
        return response
    }

    //unsubscribe to notification
    override suspend fun unsubscribeToNotification(token: String, fcmToken: String): Response<Unit> {
        val response = api.unsubscribeToNotification("Bearer $token", fcmToken)
        if (response.isSuccessful) {
            Log.d("NotificationRepositoryImpl", "unsubscribeToNotification: ${response.code()}")
            Result.success(response)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    throw Exception("Bad Request")
                }
                401 -> {
                    throw Exception("Unauthorized")
                }
                403 -> {
                    throw Exception("Forbidden")
                }
                404 -> {
                    throw Exception("Not Found")
                }
                500 -> {
                    throw Exception("Internal Server Error")
                }
                else -> {
                    throw Exception("Unknown Error")
                }
            }
            Log.d("NotificationRepositoryImpl", "unsubscribeToNotification: ${response.code()}")
            Result.failure(Exception("Failed to unsubscribe to notification"))
        }
        return response
    }

    //read
    override suspend fun readNotification(token: String, notificationId: Long): Response<Unit> {
        val response = api.readNotification("Bearer $token", notificationId)
        if (response.isSuccessful) {
            Result.success(response)
        } else {
            val httpCode = response.code()
            when (httpCode) {
                400 -> {
                    throw Exception("Bad Request")
                }
                401 -> {
                    throw Exception("Unauthorized")
                }
                403 -> {
                    throw Exception("Forbidden")
                }
                404 -> {
                    throw Exception("Not Found")
                }
                500 -> {
                    throw Exception("Internal Server Error")
                }
                else -> {
                    throw Exception("Unknown Error")
                }
            }
            Result.failure(Exception("Failed to read notification"))
        }
        return response
    }



}