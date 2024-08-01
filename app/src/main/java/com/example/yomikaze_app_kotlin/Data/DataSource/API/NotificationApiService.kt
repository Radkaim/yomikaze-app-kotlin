package com.example.yomikaze_app_kotlin.Data.DataSource.API

import com.example.yomikaze_app_kotlin.Domain.Models.NotificationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface NotificationApiService {

    @GET("notification")
    suspend fun getNotifications(@Header("Authorization") token: String): List<NotificationResponse>

    //subribe to notification
    @FormUrlEncoded
    @POST("notification/subscribe")
    suspend fun subscribeToNotification(
        @Header("Authorization") token: String,
        @Field("fcmToken") fcmToken: String,
    ): Response<Unit>

    //unsubscribe to notification
    @FormUrlEncoded
    @POST("notification/unsubscribe")
    suspend fun unsubscribeToNotification(
        @Header("Authorization") token: String,
        @Field("fcmToken") fcmToken: String,
    ): Response<Unit>

    //read
    @PUT("notification/read")
    suspend fun readNotification(
        @Header("Authorization") token: String,
        @Body notificationId: Long,
    ): Response<Unit>


}