package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register

data class RegisterState(
    val email: String= "",
    val emailError: String? = null,

    val username: String = "",
    val usernameError: String? = null,

    val fullName: String = "",
    val fullNameError: String? = null,

    val birthday: String = "",
    val birthdayError: String? = null,

    val password: String = "",
    val passwordError: String? = null,

    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null,
)
