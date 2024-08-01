package com.example.yomikaze_app_kotlin.Domain.UseCases.Noti

import com.example.yomikaze_app_kotlin.Domain.Models.NotificationResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.NotificationRepository
import javax.inject.Inject

class GetNotificationAPIUC @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend fun getNotification(token: String): Result<List<NotificationResponse>> {
        return notificationRepository.getNotifications(token)
    }
}