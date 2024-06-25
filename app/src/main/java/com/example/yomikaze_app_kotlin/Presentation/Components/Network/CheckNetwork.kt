package com.example.yomikaze_app_kotlin.Presentation.Components.Network

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.example.yomikaze_app_kotlin.Core.Networks.ConnectionState
import com.example.yomikaze_app_kotlin.Core.Networks.connectivityState
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.R

@Composable
fun CheckNetwork() : Boolean {
    // This will cause re-composition on every network state change
    val connection by connectivityState()
    return connection === ConnectionState.Available
}

@Composable
fun NoDataAvailable() {
    LottieAnimationComponent(
        animationFileName = R.raw.not_found, // Replace with your animation file name
        loop = true,
        autoPlay = true,
        modifier = Modifier
            .fillMaxWidth()
            .scale(1.15f)
    )
}
