package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ComicDetailsView(
    comicId: Int,
    navController: NavController
) {

    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon",
                        )
                    }

                },
                actions = {
                    actionMenu(navController = navController)
                }
            )
        })
    {
        Column {
            Text(text = "$comicId")
        }
    }
}

@Composable
fun actionMenu(navController: NavController) {
    IconButton(onClick = {
        navController.navigate("search")
    }) {
        Icon(Icons.Filled.Search, contentDescription = "Search")
    }
    IconButton(onClick = {
        navController.navigate("settings")
    }) {
        Icon(Icons.Filled.Settings, contentDescription = "Settings")
    }
}