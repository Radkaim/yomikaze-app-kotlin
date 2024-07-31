package com.example.yomikaze_app_kotlin.Presentation.Screens.Notification

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.HomeBottomNavBar
import com.example.yomikaze_app_kotlin.Presentation.Components.NotSignIn.NotSignIn
import com.example.yomikaze_app_kotlin.Presentation.Components.Notification.NotificationCard
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationView(
    navController: NavController,
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {

    val state by notificationViewModel.state.collectAsState()
    val listState = rememberLazyListState()

    //set navController for viewModel
    notificationViewModel.setNavController(navController)

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 15.dp)
                    .padding(bottom = 60.dp) // for show all content
            ) {
                    items(state.listNotification) { noti ->
                        NotificationCard(
                            title = noti.title,
                            content = noti.content,
                            isRead = noti.read,
                            creationTime = noti.creationTime,
                            onClicked = {
//                                notificationViewModel.onNotificationClicked(state.listNotification[index].id)
                            }
                        )
                    }

                }
            }
        }
    }

