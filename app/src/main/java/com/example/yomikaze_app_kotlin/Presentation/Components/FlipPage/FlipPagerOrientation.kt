package com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage

sealed class FlipPagerOrientation {
     object Vertical : FlipPagerOrientation()
     object Horizontal : FlipPagerOrientation()
}