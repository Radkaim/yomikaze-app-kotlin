package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register

data class RegisterState(
    val email: String= "",
    val emailError: String? = "",
    val username: String = "",
    val usernameError: String? = "",
    val birthday: String = "",
    val birthdayError: String? = "",
    val password: String = "",
    val passwordError: String? = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String? = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)
