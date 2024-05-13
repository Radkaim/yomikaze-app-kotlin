package com.example.yomikaze_app_kotlin.Presentation.Home

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Presentation.BottomNav.BottomNavigationBar
import com.example.yomikaze_app_kotlin.Presentation.BottomNav.NavigationGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable()
fun MainScreenView(){
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBar(title = {Text("Yomikaze")},backgroundColor = MaterialTheme.colors.primary)  },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        NavigationGraph(navController = navController)
    }
}
