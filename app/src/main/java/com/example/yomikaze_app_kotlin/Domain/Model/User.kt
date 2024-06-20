package com.example.yomikaze_app_kotlin.Domain.Model

import com.google.gson.annotations.SerializedName


// Định nghĩa các request và response data classes
data class LoginRequest(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)

data class TokenResponse(
    @SerializedName("token")
    val token: String
)

data class RegisterRequest(
    val email: String,
    val username: String,
    val dateOfBirth: String,
    val password: String,
    val confirmPassword: String
)

data class ForgotPasswordRequest(val email: String)

data class ResetPasswordRequest(
    val password: String,
    val confirmPassword: String
)

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
    val confirmPassword: String
)

data class User(
    var id: Int = 0,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var token: String = "",
    var createdAt: String = "",
    var updatedAt: String = "",
    var deletedAt: String = ""
)