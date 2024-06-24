package com.example.yomikaze_app_kotlin.Presentation.Screens.Main

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopAppBar.TopHomeAppBar
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.ChapterBottomNavBar
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.HomeBottomNavBar
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.NavigationGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable()
fun MainView(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            when (currentDestination?.route) {
                "home_route" -> TopHomeAppBar()
//                 "notification_route", "profile_route" -> CustomeAppBar(
//                    title = getNameOfRoute(currentDestination?.route.toString()),
//                    navigationIcon = {},
//                )
//
//                else -> CustomeAppBar(
//                    title = getNameOfRoute(currentDestination?.route.toString()),
//                    navigationIcon = {
//                        IconButton(onClick = {
//                            navController.popBackStack()
//                        }) {
//                            Icon(
//                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                                contentDescription = "Localized description"
//                            )
//                        }
//                    },
//                )
            }
        },

        bottomBar = {
            when (currentDestination?.route) {
                "view_chapter_route/{chapterId}" -> ChapterBottomNavBar(navController = navController)
                "home_route", "bookcase_route", "notification_route", "profile_route" -> HomeBottomNavBar(
                    navController = navController
                )

                else -> {}
            }
        }
    ) {
        NavigationGraph(navController = navController, viewModel = viewModel)
    }
}


// get the name before_
fun getNameOfRoute(route: String): String {
    val name = route.substringBefore("_")
    return name.capitalize()
}
