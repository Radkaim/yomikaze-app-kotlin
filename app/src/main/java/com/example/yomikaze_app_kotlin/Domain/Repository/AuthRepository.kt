package com.example.yomikaze_app_kotlin.Domain.Repository


import com.example.yomikaze_app_kotlin.Domain.Model.TokenResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<TokenResponse>
    suspend fun register(email: String, username: String, dateOfBirth: String, password: String, confirmPassword: String): Result<TokenResponse>
    suspend fun forgotPassword(email: String): Result<TokenResponse>
    suspend fun resetPassword(password: String, confirmPassword: String): Result<TokenResponse>
    suspend fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String): Result<TokenResponse>
}