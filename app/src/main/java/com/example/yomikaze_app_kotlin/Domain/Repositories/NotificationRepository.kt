package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.NotificationResponse
import retrofit2.Response

interface NotificationRepository {

    suspend fun getNotifications(token: String): Result<List<NotificationResponse>>

    //subribe to notification
    suspend fun subscribeToNotification(token: String, fcmToken: String): Response<Unit>

    //unsubscribe to notification
    suspend fun unsubscribeToNotification(token: String, fcmToken: String): Response<Unit>

    //read
    suspend fun readNotification(token: String, notificationId: Long): Response<Unit>


}