package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import com.example.yomikaze_app_kotlin.Domain.Models.UserInfoResponse

data class ProfileState (
    val isUserLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null,
    val userInfo: UserInfoResponse? = null,
    val userRole: String? = "Guest",
    val username : String? = "Guest",
)