package com.example.yomikaze_app_kotlin.Presentation.Navigation.BottomNav

import com.example.yomikaze_app_kotlin.R

sealed class BottomHomeNavItems(var title: String, var icon: Int, var screen_route: String) {
    object Home : BottomHomeNavItems("Home", R.drawable.ic_home,"home_route")
    object Bookcase : BottomHomeNavItems("Bookcase", R.drawable.ic_bookcase,"bookcase_route")
    object Notification : BottomHomeNavItems("Notification", R.drawable.ic_notification,"notification_route")
    object Profile : BottomHomeNavItems("Profile", R.drawable.ic_profile,"profile_route")
}