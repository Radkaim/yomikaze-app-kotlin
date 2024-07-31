package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.NotificationResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface NotificationApiService {

    @GET("notification")
    suspend fun getNotifications(@Header("Authorization") token: String): List<NotificationResponse>
}