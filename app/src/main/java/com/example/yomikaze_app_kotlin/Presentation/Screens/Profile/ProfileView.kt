package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppThemeSate
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainEvent
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.ui.AppTheme

@Composable
fun ProfileView(
    navController: NavController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Profile Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
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
    }




    Button(onClick = {

        navController.navigate("login_route")
    }) {
        Text(text = "Go to Login")

    }


}