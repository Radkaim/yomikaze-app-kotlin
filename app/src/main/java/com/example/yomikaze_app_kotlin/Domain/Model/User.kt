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