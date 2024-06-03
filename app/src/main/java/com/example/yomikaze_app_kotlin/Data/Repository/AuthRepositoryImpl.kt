package com.example.yomikaze_app_kotlin.Data.Repository

import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LoginRequest
import com.example.yomikaze_app_kotlin.Data.DataSource.API.LoginResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val appPreference: AppPreference
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        val result = api.login(LoginRequest(email, password))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            return Result.success(result.body()!!)
        }
        return Result.failure(Exception("Login failed"))
    }
}