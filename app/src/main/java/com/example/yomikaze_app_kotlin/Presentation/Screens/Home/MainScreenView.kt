package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Presentation.Navigation.NavigationGraph
import com.example.yomikaze_app_kotlin.Presentation.Screens.BottomNav.BottomNavigationBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable()
fun MainScreenView(viewModel: MainViewModel){
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBar(title = {Text("Yomikaze")},backgroundColor = MaterialTheme.colors.primary)  },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {

        NavigationGraph(navController = navController, viewModel = viewModel)
    }
}
