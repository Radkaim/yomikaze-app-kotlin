package com.example.yomikaze_app_kotlin.Presentation.Screens.Notifi

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationView() {
    //
    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "Notification",
                navigationIcon = {},
            )
        })
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Library Screen",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}
