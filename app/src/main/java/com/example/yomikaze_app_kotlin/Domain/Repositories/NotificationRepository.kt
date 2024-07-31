package com.example.yomikaze_app_kotlin.Domain.Repositories

import com.example.yomikaze_app_kotlin.Domain.Models.NotificationResponse

interface NotificationRepository {

    suspend fun getNotifications(token: String): Result<List<NotificationResponse>>
}