package com.example.yomikaze_app_kotlin.Domain.Repository


import com.example.yomikaze_app_kotlin.Domain.Model.ChangePasswordResponse
import com.example.yomikaze_app_kotlin.Domain.Model.ForgotPasswordResponse
import com.example.yomikaze_app_kotlin.Domain.Model.LoginResponse
import com.example.yomikaze_app_kotlin.Domain.Model.RegisterResponse
import com.example.yomikaze_app_kotlin.Domain.Model.ResetPasswordResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<LoginResponse>
    suspend fun register(email: String, username: String, dateOfBirth: String, password: String, confirmPassword: String): Result<RegisterResponse>
    suspend fun forgotPassword(email: String): Result<ForgotPasswordResponse>
    suspend fun resetPassword(password: String, confirmPassword: String): Result<ResetPasswordResponse>
    suspend fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String): Result<ChangePasswordResponse>
}