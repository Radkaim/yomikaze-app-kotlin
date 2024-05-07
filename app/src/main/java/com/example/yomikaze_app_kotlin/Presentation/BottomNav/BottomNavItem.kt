package com.example.yomikaze_app_kotlin.Presentation.BottomNav

import com.example.yomikaze_app_kotlin.R

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Home : BottomNavItem("Home", R.drawable.icon_home,"home_route")
    object Bookcase : BottomNavItem("Library", R.drawable.ic_library,"bookcase_route")
    object Profile : BottomNavItem("Profile", R.drawable.ic_library,"profile_route")
}