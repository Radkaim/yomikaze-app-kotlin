package com.example.yomikaze_app_kotlin.Domain.Models


data class ErrorResponse(
    val success: Boolean,
    val message: String,
    val errors: Map<String, List<String>>? = null
)

data class ErrorDetail(
    val code: String,
    val description: String
)

data class ErrorPasswordChange(
    val errors: List<ErrorDetail>
)