package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.TopAppBar.TopHomeAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Main.MainViewModel
import com.example.yomikaze_app_kotlin.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileView(
    navController: NavController,
    viewModel: MainViewModel,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    profileViewModel.setNavController(navController)
    val state by profileViewModel.state.collectAsState()




    Scaffold(
        topBar = {
            TopHomeAppBar( navigationIcon = {},
                actions = {},
                isProfile = true,
                onLogoClicked = {},
                onSearchClicked = {},
                onSettingClicked = {profileViewModel.onSettingCLicked()}
            )
        })
    {

        if (CheckNetwork()) {
            // Show UI when connectivity is available
           ProfileContent(state = state, profileViewModel = profileViewModel , navController = navController, viewModel = viewModel )
        } else {
            ProfileContent(state = state, profileViewModel = profileViewModel , navController = navController, viewModel = viewModel )
            // Show UI for No Internet Connectivity
            NetworkDisconnectedDialog()
           // NoDataAvailable()
        }
    }
}

@Composable
fun ProfileContent(
    state: ProfileState,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    viewModel: MainViewModel
) {
    profileViewModel.getUserInfo(CheckNetwork())

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .offset(y = (-160).dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()

                .wrapContentSize(Alignment.Center)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_personal), // replace with your avatar drawable
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDADADA))
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Guest Text
            state.username?.let { it1 -> Text(text = it1, fontWeight = FontWeight.Bold, fontSize = 24.sp) }

            // Subtitle Text
            state.userRole?.let { it1 ->
                Text(
                    text = it1,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ) }

            Spacer(modifier = Modifier.height(32.dp))


            // Sign In Button
            if (!profileViewModel.checkUserIsLogin()) {
                Button(
                    onClick = { profileViewModel.onSignInButtonClicked() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(text = "Sign In", color = Color.White)
                }
            }

        }
    }
}