package com.example.yomikaze_app_kotlin.Presentation.Screens.Notifi

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar
import com.example.yomikaze_app_kotlin.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationView() {
    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "Notification",
                navigationIcon = {},
            )
        })

    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        )
        {
            LottieAnimationComponent(
                animationFileName = R.raw.no_connection_2, // Replace with your animation file name
                loop = true,
                autoPlay = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(0.15f)
            )
        }
    }
}
