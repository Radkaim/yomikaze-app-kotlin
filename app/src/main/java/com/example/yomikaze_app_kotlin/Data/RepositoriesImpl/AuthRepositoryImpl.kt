package com.example.yomikaze_app_kotlin.Data.RepositoriesImpl

import android.util.Log
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Data.DataSource.API.AuthApiService
import com.example.yomikaze_app_kotlin.Domain.Models.ErrorResponse
import com.example.yomikaze_app_kotlin.Domain.Models.LoginRequest
import com.example.yomikaze_app_kotlin.Domain.Models.RegisterRequest
import com.example.yomikaze_app_kotlin.Domain.Models.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Models.UserInfoResponse
import com.example.yomikaze_app_kotlin.Domain.Repositories.AuthRepository
import com.google.gson.Gson
import javax.inject.Inject
import kotlin.Result.Companion.failure


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val appPreference: AppPreference
) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): Result<TokenResponse> {
        val result = api.login(LoginRequest(loginRequest.username, loginRequest.password))
        if (result.isSuccessful) {
            val tokenResponse = result.body()
            if (tokenResponse?.token != null) {
                appPreference.authToken = tokenResponse.token
                return Result.success(tokenResponse)
            }
        }

        val errorResponse = result.errorBody()?.string()
        if (errorResponse != null) {
            return try {
                val error = Gson().fromJson(errorResponse, ErrorResponse::class.java)
                Log.d("AuthRepositoryImpl", "login: ${error.message}")
                Result.failure(Exception(error.message))
            } catch (e: Exception) {
                Result.failure(Exception("Login failed"))
            }
        }

        return Result.failure(Exception("Login failed"))
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

    override suspend fun logout(){
        val result = appPreference.authToken?.let { appPreference.deleteUserToken() }
        if (result != null) {
            Log.d("AuthRepositoryImpl", "logout: $result")
        }
    }

}