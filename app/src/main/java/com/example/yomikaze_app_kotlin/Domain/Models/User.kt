package com.example.yomikaze_app_kotlin.Domain.Models

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName


/**
 * Login
 */
data class LoginRequest(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("twoFactorCode")
    val twoFactorCode: String? = null // Thêm trường tùy chọn
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
    @SerializedName("currentPassword")
    val currentPassword: String? = null,

    @SerializedName("newPassword")
    val newPassword: String
)


/**
 * User Info
 */


data class UserInfoResponse(
    //for api
    @SerializedName("user")
    //for database
    @ColumnInfo(name = "user")
    val user: User,
)

data class ProfileResponse(

    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String,

    @SerializedName("balance")
    var balance: Long,

    @SerializedName("avatar")
    var avatar: String? = null,

    @SerializedName("roles")
    var roles: List<String>? = null,

    @SerializedName("birthday")
    var birthday: String = "",
)


data class User(
    //for api
    @SerializedName("id")
    //for database
    @ColumnInfo(name = "id")
    val id: Long,

    //for api
    @SerializedName("name")
    //for database
    @ColumnInfo(name = "name")
    val name: String,

    //for api
    @SerializedName("balance")
    //for database
    @ColumnInfo(name = "balance")
    val balance: Int,
)
