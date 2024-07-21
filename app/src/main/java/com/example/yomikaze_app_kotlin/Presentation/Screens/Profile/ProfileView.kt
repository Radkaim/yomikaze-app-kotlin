package com.example.yomikaze_app_kotlin.Presentation.Screens.Profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.TopAppBar.DefaultTopAppBar
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
            DefaultTopAppBar(navigationIcon = {},
                actions = {},
                isProfile = true,
                onLogoClicked = {},
                onSearchClicked = {},
                onSettingClicked = { profileViewModel.onSettingCLicked() }
            )
        })
    {

        if (CheckNetwork()) {
            // Show UI when connectivity is available
            ProfileContent(
                state = state,
                profileViewModel = profileViewModel,
                navController = navController,
                viewModel = viewModel
            )
        } else {
            ProfileContent(
                state = state,
                profileViewModel = profileViewModel,
                navController = navController,
                viewModel = viewModel
            )
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
//    profileViewModel.getUserInfo(CheckNetwork())

    LaunchedEffect(Unit) {
        profileViewModel.getProfile()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .offset(y = (-230).dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(140.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(100.dp)
                        )
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(APIConfig.imageAPIURL.toString() + state.profileResponse?.avatar)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        placeholder = painterResource(R.drawable.ic_profile),
                        error = painterResource(R.drawable.ic_profile),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(90.dp)
                            .height(90.dp)
                            .blur(2.dp)
                            .align(Alignment.Center)

                    )
                }
            }

            item {
                Text(
                    text = state.profileResponse?.name ?: "Guest",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                // Subtitle Text

                Text(
                    text = state.profileResponse?.roles?.firstOrNull() ?: "Guest",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            item {
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
}