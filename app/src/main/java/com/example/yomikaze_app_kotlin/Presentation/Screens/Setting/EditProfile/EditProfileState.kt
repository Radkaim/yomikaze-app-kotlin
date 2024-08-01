package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.EditProfile

import com.example.yomikaze_app_kotlin.Domain.Models.ImageResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse

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

    val profileResponse: ProfileResponse? = null,
    val isGetProfileLoading: Boolean = false,

    val imageResponse: ImageResponse? = null,
    val isUploadImageLoading: Boolean = false,
)