package com.example.yomikaze_app_kotlin.Presentation.Screens.Notification

import com.example.yomikaze_app_kotlin.Domain.Models.NotificationResponse

data class NotificationState(
    val isNotificationLoading : Boolean = false,
    val listNotification : List<NotificationResponse> = emptyList(),
)