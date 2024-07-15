package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch

import android.annotation.SuppressLint
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NoNetworkAvailable
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar

@Composable
fun AdvancedSearchView(
    navController: NavController,
    comicNameSearchText: String?,
    advancedSearchViewModel: AdvancedSearchViewModel = hiltViewModel(),
) {
    val state by advancedSearchViewModel.state.collectAsState()

    if (CheckNetwork()){
        AdvancedSearchContent(
            navController = navController,
            comicNameSearchText = comicNameSearchText,
        )
    }else
    {
       NoNetworkAvailable()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdvancedSearchContent(
    navController: NavController,
    comicNameSearchText: String?,
) {

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Avanced Search",
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
        },
    ) {

    }
}