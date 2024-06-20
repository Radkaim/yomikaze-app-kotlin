package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile.Setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile.SettingItem
import com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile.SettingObject
import com.example.yomikaze_app_kotlin.R

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp), // Reduced vertical spacing
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        listIconSettingProfile.forEach{ settingObject ->
            SettingItem(
                iconStart = settingObject.iconStart,
                title = settingObject.title,
                iconEnd = settingObject.iconEnd,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_darkmode),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            SwitchDarkMode()
        }


    }

}
@Composable
fun SwitchDarkMode() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        }
    )
}


