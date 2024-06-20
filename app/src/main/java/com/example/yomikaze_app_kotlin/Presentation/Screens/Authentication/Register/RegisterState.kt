package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register

data class RegisterState(
    val email: String= "",
    val emailError: String? = "",
    val username: String = "",
    val usernameError: String? = "",
    val dateOfBirth: String = "",
    val dateOfBirthError: String? = "",
    val password: String = "",
    val passwordError: String? = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String? = "",
    val isLoading: Boolean = true,
    val error: String? = null,
)
