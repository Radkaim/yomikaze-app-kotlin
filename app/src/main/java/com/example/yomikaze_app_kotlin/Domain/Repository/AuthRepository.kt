package com.example.yomikaze_app_kotlin.Domain.Repository


import com.example.yomikaze_app_kotlin.Domain.Model.TokenResponse
import com.example.yomikaze_app_kotlin.Domain.Model.UserInfoResponse

/**
 * AuthRepository is an interface for the AuthRepositoryImpl class.
 */
interface AuthRepository {
    suspend fun login(username: String, password: String): Result<TokenResponse>
    suspend fun register(
        username: String,
        password: String,
        fullName: String,
        confirmPassword: String,
        email: String,
        birthday: String
    ): Result<TokenResponse>
//    suspend fun forgotPassword(email: String): Result<TokenResponse>
//    suspend fun resetPassword(password: String, confirmPassword: String): Result<TokenResponse>
//    suspend fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String): Result<TokenResponse>

    suspend fun getUserInfo(token: String): Result<UserInfoResponse>
}