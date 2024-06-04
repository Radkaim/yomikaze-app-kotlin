package com.example.yomikaze_app_kotlin.Presentation.Components.BottomNav

import com.example.yomikaze_app_kotlin.R

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Home : BottomNavItem("Home", R.drawable.icon_home,"home_route")
    object Bookcase : BottomNavItem("Bookcase", R.drawable.ic_bookcase,"bookcase_route")
    object Notification : BottomNavItem("Notification", R.drawable.ic_notification,"notification_route")
    object Profile : BottomNavItem("Profile", R.drawable.ic_profile,"profile_route")
    object Login : BottomNavItem("Login", R.drawable.ic_library,"login_route")
}