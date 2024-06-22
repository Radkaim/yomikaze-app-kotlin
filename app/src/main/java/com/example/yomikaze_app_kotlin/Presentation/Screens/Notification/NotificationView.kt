package com.example.yomikaze_app_kotlin.Presentation.Screens.Notification

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.ChapterCard
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationView() {
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Notification",
                navigationIcon = {},
            )
        })

    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        )
        {
//            LottieAnimationComponent(
//                animationFileName = R.raw.no_connection_2, // Replace with your animation file name
//                loop = true,
//                autoPlay = true,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .scale(0.15f)
//            )
            Column {
                ChapterCard(
                    chapterIndex = 0,
                    title = "Superhero Origins",
                    views = 100000,
                    comments = 1000,
                    publishedDate = "27/01/2024",
                    isLocked = false,
                    onClick = {},
                    onReportClick = {}
                )
                Spacer(modifier = Modifier.height(1.dp))
                ChapterCard(
                    chapterIndex = 1,
                    title = "Superhero Origins12",
                    views = 1000,
                    comments = 10,
                    publishedDate = "27/01/2024",
                    isLocked = true,
                    onClick = {},
                    onReportClick = {}
                )
            }
        }
    }
}
