package com.example.yomikaze_app_kotlin.Domain.Model

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