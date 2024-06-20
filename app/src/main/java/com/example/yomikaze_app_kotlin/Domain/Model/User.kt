package com.example.yomikaze_app_kotlin.Domain.Model

import com.google.gson.annotations.SerializedName


// Định nghĩa các request và response data classes
data class LoginRequest(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)

data class LoginResponse(

    @SerializedName("token")
    val token: String
)


//email, username, dateOfBirth, password, confirmPassword
data class RegisterRequest(val email: String, val username: String, val dateOfBirth: String, val password: String, val confimPassword: String)
data class RegisterResponse(val token: String)

data class ForgotPasswordRequest(val email: String)
data class ForgotPasswordResponse(val token: String)

data class ResetPasswordRequest(val password: String, val confirmPassword: String)
data class ResetPasswordResponse(val token: String)


data class User(
    var id: Int = 0,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var token: String = "",
    var created_at: String = "",
    var updated_at: String = "",
    var deleted_at: String = ""
)