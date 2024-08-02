package com.example.yomikaze_app_kotlin.Domain.Models


data class ErrorResponse(
    val success: Boolean,
    val message: String,
    val errors: Map<String, List<String>>? = null
)
