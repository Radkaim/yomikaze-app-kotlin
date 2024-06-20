package com.example.yomikaze_app_kotlin.Domain.Repository

import com.example.yomikaze_app_kotlin.Data.DataSource.API.ForgotPasswordResponse

import com.example.yomikaze_app_kotlin.Data.DataSource.API.RegisterResponse
import com.example.yomikaze_app_kotlin.Data.DataSource.API.ResetPasswordResponse
import com.example.yomikaze_app_kotlin.Domain.Model.LoginResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<LoginResponse>
    suspend fun register(email: String, username: String, dateOfBirth: String, password: String, confirmPassword: String): Result<RegisterResponse>
    suspend fun forgotPassword(email: String): Result<ForgotPasswordResponse>
    suspend fun resetPassword(password: String, confirmPassword: String): Result<ResetPasswordResponse>
}