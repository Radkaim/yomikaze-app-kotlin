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
    @SerializedName("oldPassword")
    val oldPassword: String,

    @SerializedName("newPassword")
    val confirmPassword: String
)

/**
 * User Info
 */
//
//data class UserInfoResponse(
//
//    @SerializedName("success")
//    val success: Boolean,
//
//    @SerializedName("message")
//    val message: String,
//
//    //for api
//    @SerializedName("data")
//    //for database
//    @ColumnInfo(name = "user_data")
//    val data: UserData
//)

data class UserInfoResponse(
    //for api
    @SerializedName("user")
    //for database
    @ColumnInfo(name = "user")
    val user: User,
)

data class DataUser(
    //for api
    @SerializedName("user")
    //for database
    @ColumnInfo(name = "user")
    val user: User
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

//    //for api
//    @SerializedName("email")
//    //for database
//    @ColumnInfo(name = "email")
//    val email: String,
//
//    //for api
//    @SerializedName("isAdmin")
//    //for database
//    @ColumnInfo(name = "isAdmin")
//    val isAdmin: Boolean,
)

data class UserClaims(

    @SerializedName("jti")
    val jti: String,

    @SerializedName("iat")
    val iat: String,

    @SerializedName("exp")
    val exp: String,

    @SerializedName("sub")
    val sub: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("sid")
    val sid: String,

    @SerializedName("roles")
    val roles: String
)