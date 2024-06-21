package com.example.yomikaze_app_kotlin.Data.Repository

import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Domain.Model.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Model.RegisterRequest
import com.example.yomikaze_app_kotlin.Domain.Model.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Model.UserInfoResponse
import com.example.yomikaze_app_kotlin.Domain.Repository.AuthRepository
import javax.inject.Inject
import kotlin.Result.Companion.failure

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val appPreference: AppPreference
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<TokenResponse> {
        val result = api.login(LoginRequest(username, password))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            return Result.success(result.body()!!)
        }
        return failure(Exception("Login failed"))
    }

    override suspend fun register(
        username: String,
        password: String,
        fullName: String,
        confirmPassword: String,
        email: String,
        birthday: String
    ): Result<TokenResponse> {
        val result = api.register(RegisterRequest(username, password, fullName, confirmPassword, email, birthday))
        if (result.isSuccessful) {
            appPreference.authToken = result.body()?.token
            return Result.success(result.body()!!)
        }
        return failure(Exception("Register failed"))
    }

    override suspend fun getUserInfo(token: String): Result<UserInfoResponse> {
        val result = api.getUserInfo("Bearer $token")
        if (result.isSuccessful) {
            return Result.success(result.body()!!)
        }
        return failure(Exception("Get user info failed"))
    }

}