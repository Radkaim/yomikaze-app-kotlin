package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.EditProfile

import com.example.yomikaze_app_kotlin.Domain.Models.ImageResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse

data class EditProfileState(
    val username: String? = null,
    val dateOfBirth: String? = null,
    val bio: String ? = null,

    val isLoading: Boolean = true,
    val error: String? = null,

    val profileResponse: ProfileResponse? = null,
    val isGetProfileLoading: Boolean = false,

    val imageResponse: ImageResponse? = null,
    val isUploadImageLoading: Boolean = false,

    val isUpdateProfileSuccess: Boolean = false,
)