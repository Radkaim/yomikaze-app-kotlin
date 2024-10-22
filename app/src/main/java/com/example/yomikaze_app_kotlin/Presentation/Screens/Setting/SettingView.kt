package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile.SettingItemComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile.SettingObject
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.R
import com.example.yomikaze_app_kotlin.ui.AppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingView(
    navController: NavController,
    viewModel: MainViewModel,
    settingViewModel: SettingViewModel = hiltViewModel(),
) {
    settingViewModel.setNavController(navController)
    settingViewModel.setMainViewModel(viewModel)
    val state by settingViewModel.state.collectAsState()

    val context = LocalContext.current
    val appPreference = AppPreference(context)

    val listIconSettingProfile = listOf(
        SettingObject(
            iconStart = R.drawable.ic_login_logout,
            title = "Login Method",
            iconEnd = if (appPreference.isUserLoggedIn) {
                if (appPreference.isLoginWithGoogle) {
                    R.drawable.ic_google
                } else {
                    R.drawable.logo
                }
            } else {
                R.drawable.ic_next
            },
            route = "login_route",
            isLoginMethod = appPreference.isUserLoggedIn
        ),
        SettingObject(
            iconStart = R.drawable.ic_edit,
            title = "Edit Profile",
            iconEnd = R.drawable.ic_next,
            route = "edit_profile_route"
        ),
        SettingObject(
            iconStart = R.drawable.ic_changepassword,
            title = "Change Password",
            iconEnd = R.drawable.ic_next,
            route = "change_password_route"
        ),
        //  SettingObject(R.drawable.ic_darkmode, "Dark Mode", R.drawable.ic_next, ),
        SettingObject(
            iconStart = R.drawable.ic_coins,
            iconStartColor = MaterialTheme.colorScheme.scrim,
            title = "Coins Shop",
            iconEnd = R.drawable.ic_next,
            route = "coins_shop_route"
        ),
        SettingObject(
            iconStart = R.drawable.ic_about_us,
            title = "About Us",
            iconEnd = R.drawable.ic_next,
            route = "about_us_route"
        ),
        SettingObject(
            iconStart = R.drawable.ic_history,
            title = "Transaction History",
            iconEnd = R.drawable.ic_next,
            route = "transaction_history_route"
        ),
        SettingObject(
            iconStart = R.drawable.ic_search,
            title = "Advanced Search",
            iconEnd = R.drawable.ic_next,
            route = "advance_search_route"
        ),

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
        }

        )
    {
        Divider(
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
            thickness = 1.dp,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 10.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp), // Reduced vertical spacing
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            listIconSettingProfile.forEach { settingObject ->
                SettingItemComponent(
                    iconStart = settingObject.iconStart,
                    iconStartColor = settingObject.iconStartColor,
                    title = settingObject.title,
                    iconEnd = settingObject.iconEnd,
                    isLoginMethod = settingObject.isLoginMethod,
                    onClicked = { settingViewModel.onSettingItemCLicked(settingObject.route, context) }
                )
            }
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_darkmode),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.75f),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Dark Mode", fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SwitchCustomComponent(settingViewModel)

                }
                Spacer(modifier = Modifier.width(16.dp))
                Divider(
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                    thickness = 1.dp,
                )

                if (settingViewModel.checkUserIsLogin()) {
                    Log.d("SettingView", "${settingViewModel.checkUserIsLogin()}")
                    Button(
                        onClick = { settingViewModel.onLogout() },
                        modifier = Modifier
                            .width(125.dp)
                            .height(60.dp)
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        )

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_login_logout),
                            contentDescription = "Logout",
                            modifier = Modifier
                                .size(15.dp)
                                .offset(x = (-10).dp),
                            tint = Color(0xFFffF2FFFD)
                        )
                        Text(
                            text = "LOGOUT",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFffF2FFFD),
                        )

                    }
                }
            }

        }
    }

}

//}
@Composable
fun SwitchCustomComponent(
    settingViewModel: SettingViewModel
) {
    val context = LocalContext.current
    val appPreference = AppPreference(context)
    var checked by remember { mutableStateOf(true) }

    checked = appPreference.getTheme() != AppTheme.LIGHT

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
            settingViewModel.changeTheme()
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
            checkedTrackColor = MaterialTheme.colorScheme.inverseOnSurface,
            uncheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
            uncheckedTrackColor = MaterialTheme.colorScheme.inverseOnSurface,
        ),
        modifier = Modifier
            .width(50.dp)
            .height(20.dp)
            .scale(0.8f)
    )
}


