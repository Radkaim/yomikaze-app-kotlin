package com.example.yomikaze_app_kotlin.Presentation.BottomNav

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Presentation.Bookcase.BookcaseView
import com.example.yomikaze_app_kotlin.Presentation.Home.HomeView
import com.example.yomikaze_app_kotlin.Presentation.Profile.ProfileView
import com.example.yomikaze_app_kotlin.ui.AppTheme
import com.example.yomikaze_app_kotlin.ui.YomikazeappkotlinTheme


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {

        composable(BottomNavItem.Home.screen_route) {
            HomeView()
        }
        composable(BottomNavItem.Bookcase.screen_route) {
            BookcaseView()
        }
        composable(BottomNavItem.Profile.screen_route) {
            ProfileView()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        BottomNavItem.Bookcase,
        BottomNavItem.Home,
        BottomNavItem.Profile
    )
    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.onPrimary, contentColor = MaterialTheme.colorScheme.primary, elevation = 8.dp) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title, tint = MaterialTheme.colorScheme.primary) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.primary,
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    YomikazeappkotlinTheme(appTheme = AppTheme.Light) {
        val navController = rememberNavController()
        BottomNavigationBar(navController)
    }

}

