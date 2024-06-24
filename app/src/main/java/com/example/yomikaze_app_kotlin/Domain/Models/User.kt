package com.example.yomikaze_app_kotlin.Domain.Models

import com.google.gson.annotations.SerializedName



/**
 * Login
 */
data class LoginRequest(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)

/**
 * Token
 */
data class TokenResponse(
    @SerializedName("token")
    val token: String
)

/**
 * Register
 */
data class RegisterRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("fullName")
    val fullName: String,

    @SerializedName("confirmPassword")
    val confirmPassword: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("birthday")
    val birthday: String,
)

/**
 * Forgot Password
 */
data class ForgotPasswordRequest(
    @SerializedName("email")
    val email: String
)

/**
 * Reset Password
 */
data class ResetPasswordRequest(
    @SerializedName("confirmPassword")
    val confirmPassword: String
)

/**
 * Change Password
 */
data class ChangePasswordRequest(
    @SerializedName("oldPassword")
    val oldPassword: String,

    @SerializedName("newPassword")
    val confirmPassword: String
)

/**
 * User Info
 */
data class UserInfoResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: UserData
)

data class UserData(
    @SerializedName("data")
    val data: User,

    @SerializedName("claims")
    val claims: UserClaims
)

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("isAdmin")
    val isAdmin: Boolean,
)
data class UserClaims(
    @SerializedName("roles")
    val role : String
)