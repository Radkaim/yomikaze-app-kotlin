package com.example.yomikaze_app_kotlin.Presentation.Screens.Notification

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.HomeBottomNavBar
import com.example.yomikaze_app_kotlin.Presentation.Components.NotSignIn.NotSignIn
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationView(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val appPreference = AppPreference(context)
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Notification",
                navigationIcon = {},
            )
        },
        bottomBar = {
            HomeBottomNavBar(
                navController = navController
            )
        }
    )

    {
        if (!appPreference.isUserLoggedIn) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.background),

                ) {
                NotSignIn(navController = navController)
            }
        } else {
        }
    }
}
