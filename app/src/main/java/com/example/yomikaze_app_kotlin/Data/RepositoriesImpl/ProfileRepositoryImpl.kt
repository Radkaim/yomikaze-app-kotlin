package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import com.example.yomikaze_app_kotlin.Data.DataSource.API.ProfileApiService
import com.example.yomikaze_app_kotlin.Domain.Models.ChangePasswordRequest
import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApiService
) : ProfileRepository {
    override suspend fun getProfile(
        token: String,
    ): Result<ProfileResponse> {
        return try {
            val response = api.getProfile("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //change password
    override suspend fun changePassword(
        token: String,
        changePasswordRequest: ChangePasswordRequest
    ): Response<Unit> {
        val response = api.changePassword("Bearer $token", changePasswordRequest)
        if (response.isSuccessful) {
            Result.success(Unit)
        }
        return response
    }

}