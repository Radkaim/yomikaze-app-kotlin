package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.Setting

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile.SettingItemComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile.SettingObject
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.R

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


    val listIconSettingProfile = listOf(
        SettingObject(
            iconStart = R.drawable.ic_login_logout,
            title = "Login Method",
            iconEnd = R.drawable.ic_next,
            route = ""
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
            route = "reset_password_route"
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
                    onClicked = { settingViewModel.onSettingItemCLicked(settingObject.route) }
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
                        tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Dark Mode")
                    Spacer(modifier = Modifier.weight(1f))
                    SwitchCustomComponent(settingViewModel)

                }
                Spacer(modifier = Modifier.width(16.dp))
                Divider(
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                    thickness = 1.dp,
                )

                if (settingViewModel.checkUserIsLogin()){
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
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "LOGOUT",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
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
    var checked by remember { mutableStateOf(true) }

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
    )
}


