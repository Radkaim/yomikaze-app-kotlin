package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.EditProfile

data class EditProfileState(
    val username: String,
    val usernameError: String,
    val password: String,
    val passwordError: String,
    val dateOfBirth: String,
    val dateOfBirthError: String,
    val aboutMe: String,
    val aboutMeError: String,
    val isLoading: Boolean = true,
    val error: String? = null,
)