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
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile.SettingItems
import com.example.yomikaze_app_kotlin.R

@Composable
fun SettingView(
    settingViewModel: SettingViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by settingViewModel.state.collectAsState()
    var checked by remember { mutableStateOf(true) }

    settingViewModel.setNavController(navController)

    SettingContent(state, settingViewModel, navController)
}

@Composable
fun SettingContent(
    state: SettingState,
    viewModel: SettingViewModel,
    navController: NavController
) {
    val settings = getListSetting() // test data

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 1.dp)
                .fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.weight(1f))
        }
        showSetting(state, navController)
    }
}

fun getListSetting(): List<SettingItems> {
    return listOf(
        SettingItems(
            login = "Login",
            editProfile = "Edit Profile",
            changePassword = "Change Password",
            darkMode = "Dark Mode",
            coinsShop = "Coins Shop",
            aboutUs = "About Us",
            transactionHistory = "Transaction History",
        )
    )
}

@Composable
fun showSetting(state: SettingState, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 1.dp)
                .fillMaxWidth()
        ) {

        }
//
    }
    showSettingProfile()
}

@Composable
fun showSettingProfile() {
    val settings = getListSetting()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp), // Reduced vertical spacing
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        settings.forEach { setting ->
            SettingItem(
                setting = setting
            )

        }
    }
}

@Composable
fun SettingItem(setting: SettingItems) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_login_logout),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = setting.login,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
                modifier = Modifier.size(20.dp)
            )
        }
    }

    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        thickness = 1.dp,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = setting.editProfile,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        thickness = 1.dp,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_changepassword),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = setting.changePassword,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        thickness = 1.dp,
    )
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
        Text(
            text = setting.darkMode,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        SwitchDarkMode()
    }
    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        thickness = 1.dp,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_coins),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.scrim,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = setting.coinsShop,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        thickness = 1.dp,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_about_us),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = setting.aboutUs,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        thickness = 1.dp,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_history),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = setting.transactionHistory,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.75f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        thickness = 1.dp,
    )
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

@Preview
@Composable
fun SettingContentPreview() {
    val navController = rememberNavController()
    SettingContent(state = SettingState(), viewModel = SettingViewModel(), navController = navController)
}
