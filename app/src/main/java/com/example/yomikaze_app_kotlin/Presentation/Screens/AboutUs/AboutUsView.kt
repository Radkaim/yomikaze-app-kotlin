package com.example.yomikaze_app_kotlin.Presentation.Screens.AboutUs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.AboutUs.AboutUsItem
import com.example.yomikaze_app_kotlin.Presentation.Components.AboutUs.AboutUsObject
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar
import com.example.yomikaze_app_kotlin.R

@Composable
fun AboutUsView(navController: NavController) {
    val listAboutUs = listOf(
        AboutUsObject(title = "Facebook", titleEnd = "@Yomikaze"),
        AboutUsObject(title = "Email", titleEnd = "yomikaze@gmail.com"),
        AboutUsObject(title = "Term of Service", iconEnd = R.drawable.ic_next),
        AboutUsObject(title = "Privacy Policy", iconEnd = R.drawable.ic_next)
    )

    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "About Us",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Back Icon"
                        )
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
//                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )

            listAboutUs.forEach { AboutUsObject ->
                AboutUsItem(aboutUsObjectIcon = AboutUsObject)
            }
        }
    }
}