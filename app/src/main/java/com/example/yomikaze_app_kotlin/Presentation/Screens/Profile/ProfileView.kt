package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppThemeSate
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainEvent
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.R
import com.example.yomikaze_app_kotlin.ui.AppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileView(
    navController: NavController,
    viewModel: MainViewModel,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    profileViewModel.setNavController(navController)

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Profile",
                navigationIcon = {},
            )
        })
    {
        Box {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .wrapContentSize(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_personal), // replace with your avatar drawable
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDADADA))
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Guest Text
                Text(text = "Guest", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(4.dp))
                // Subtitle Text
                Text(text = "Guest", color = Color.Red)
                Spacer(modifier = Modifier.height(32.dp))
                // Sign In Button
                Button(
                    onClick = { /* Handle Sign In */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373))
                ) {
                    Text(text = "Sign In", color = Color.White)
                }
                Button(
                    onClick =
                    {

                        val currentTheme = viewModel.stateApp
                        val newTheme = when (currentTheme.theme) {
                            AppTheme.DARK -> AppTheme.LIGHT
                            AppTheme.LIGHT -> AppTheme.DARK
                            AppTheme.DEFAULT -> AppTheme.DARK

                            else -> {
                                AppTheme.DEFAULT
                            }
                        }
                        AppThemeSate.resetTheme()
                        AppThemeSate.setTheme(newTheme)
                        viewModel.onEvent(MainEvent.ThemeChange(newTheme))
                    },
                ) {
                    Text(text = "Change Theme")
                }

                Button(onClick = {
                    navController.navigate("setting_route")
                }) {
                    Text(text = "Go to setting")
                }

                Button(onClick = {
                    navController.navigate("aboutUs_route")
                }) {
                    Text(text = "Go to aboutUs_route")
                }

                Button(onClick = {
                    profileViewModel.onLogout()
                }) {
                    Text(text = "Go to Logout")
                }
            }
        }


    }
}