package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DownloadDetailView(
    navController: NavController,
    comicId: Long,
    comicName: String
) {

    //val preBackStack = navController.currentBackStackEntry?.destination?.route

   // Log.d("DownloadDetailView", "preBack: $preBackStack")
    Scaffold(

        topBar = {
            CustomAppBar(
                title = "$comicName",
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
                actions = {
                    Text(
                        text = "Edit",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            //TODO: Handle Edit Click
                        }
                    )
                },
            )
        }
    )
    { paddingValues ->
        // Nội dung của bạn ở đây, có thể dùng paddingValues nếu cần
        Text(
            text = "DownloadDetailView",
            )
    }
}