package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Domain.Models.ProfileResponse
import com.example.yomikaze_app_kotlin.Domain.Models.UserInfoResponse

data class ProfileState (
    val isUserLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null,
    val userInfo: UserInfoResponse? = null,
    val userRole: String? = "Guest",
    val username : String? = "Guest",

    val profileResponse: ProfileResponse? = null,
    val isGetProfileLoading: Boolean = false,

    val comicResponse: List<ComicResponse>? = null,
    val isGetComicByRolePublisherLoading: Boolean = false,
    val totalComic: Int = 0
)