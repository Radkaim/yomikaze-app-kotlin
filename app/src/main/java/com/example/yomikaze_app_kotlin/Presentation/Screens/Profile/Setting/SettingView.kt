package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.Setting

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppThemeSate
import com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile.SettingItem
import com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile.SettingObject
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainEvent
import com.example.yomikaze_app_kotlin.R
import com.example.yomikaze_app_kotlin.ui.AppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingView(navController: NavController) {
    val listIconSettingProfile = listOf(
        SettingObject(R.drawable.ic_login_logout, "Login Method", R.drawable.ic_next),
        SettingObject(R.drawable.ic_edit, "Edit Profile", R.drawable.ic_next),
        SettingObject(R.drawable.ic_changepassword, "Change Password", R.drawable.ic_next),
      //  SettingObject(R.drawable.ic_darkmode, "Dark Mode", R.drawable.ic_next),
        SettingObject(R.drawable.ic_coins, "Coins Shop", R.drawable.ic_next),
        SettingObject(R.drawable.ic_about_us, "About Us", R.drawable.ic_next),
        SettingObject(R.drawable.ic_history, "Change Password", R.drawable.ic_next),
    )
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Setting",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp), // Reduced vertical spacing
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            listIconSettingProfile.forEach { settingObject ->
                SettingItem(
                    iconStart = settingObject.iconStart,
                    title = settingObject.title,
                    iconEnd = settingObject.iconEnd,
                )
            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
//                    .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(modifier = Modifier.padding(start = 5.dp).size(24.dp),
                        painter = painterResource(id = R.drawable.ic_darkmode,),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
//                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Dark Mode")
                    Spacer(modifier = Modifier.weight(1f))
                    SwitchDarkMode()

                }
                Spacer(modifier = Modifier.width(16.dp))
                Divider(
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                    thickness = 1.dp,
                )
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.background)
//                        .wrapContentSize(Alignment.Center)
//                ) {
//                    androidx.compose.material3.Text(
//                        text = "Profile Screen",
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White,
//                        modifier = Modifier.align(Alignment.CenterHorizontally),
//                        textAlign = TextAlign.Center,
//                        fontSize = 20.sp
//                    )
//                    Button(
//                        onClick =
//                        {
//
//                            val currentTheme = viewModel.stateApp
//                            val newTheme = when (currentTheme.theme) {
//                                AppTheme.DARK -> AppTheme.LIGHT
//                                AppTheme.LIGHT -> AppTheme.DARK
//                                AppTheme.DEFAULT -> AppTheme.DARK
//
//                                else -> {
//                                    AppTheme.DEFAULT
//                                }
//                            }
//                            AppThemeSate.resetTheme()
//                            AppThemeSate.setTheme(newTheme)
//                            viewModel.onEvent(MainEvent.ThemeChange(newTheme))
//                        },
//                    ) {
//                        androidx.compose.material3.Text(text = "Change Theme")
//                    }
                    Button(onClick = {
                        navController.navigate("reset_password_route")
                    }) {
                        androidx.compose.material3.Text(text = "Go to reset")

                    }
                    Button(onClick = {
                        navController.navigate("edit_profile_route")
                    }) {
                        androidx.compose.material3.Text(text = "Go to edit")

                    }

//                    Button (onClick = {
//                        profileViewModel.onLogout()
//                    }) {
//                        androidx.compose.material3.Text(text = "Go to Logout")
//                    }
                }

                Button(onClick = {

                    navController.navigate("login_route")
                }) {
                    androidx.compose.material3.Text(text = "Go to Login")

                }
            }


        }
    }
//}
@Composable
fun SwitchDarkMode() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
    )
}


