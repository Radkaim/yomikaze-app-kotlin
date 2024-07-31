package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import com.example.yomikaze_app_kotlin.Data.DataSource.API.NotificationApiService
import com.example.yomikaze_app_kotlin.Domain.Models.NotificationResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.NotificationRepository
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
}