package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register

import android.annotation.SuppressLint
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterView(navController: NavController){
    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "Register",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()

                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                },
            )
        })
    {
        Text(text = "Register Screen")
    }
}