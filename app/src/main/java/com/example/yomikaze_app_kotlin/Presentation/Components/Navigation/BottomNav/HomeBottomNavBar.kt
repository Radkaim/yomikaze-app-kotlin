package com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.ui.AppTheme
import com.example.yomikaze_app_kotlin.ui.YomikazeappkotlinTheme

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeBottomNavBar(navController: NavController) {

    val items = listOf(
        BottomHomeNavItems.Bookcase,
        BottomHomeNavItems.Home,
        BottomHomeNavItems.Notification,
        BottomHomeNavItems.Profile
    )
        val context = LocalContext.current

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.primary,
        elevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val isSelected = currentRoute == item.screen_route

            // BottomNavigationItem

            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = colorSelected(isSelected, context),
                        modifier = normalSize().then(resize(isSelected))
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = if (isSelected) 12.sp else 11.sp,
                        color = colorSelected(isSelected, context),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.36f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route){
                          //                    {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = false
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = if (isSelected) Modifier.offset(y = (-4).dp) else Modifier
            )
        }
    }
}

@Composable
fun colorSelected(isSelected: Boolean, context: Context): Color {

//    val context = LocalContext.current
//    val appPreference = AppPreference(context)

    val appPreference = remember { AppPreference(context) }
    val currentTheme = rememberUpdatedState(appPreference.getTheme())

    return if (currentTheme.value == AppTheme.LIGHT || currentTheme.value == AppTheme.DEFAULT)
            if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary.copy(alpha = 0.36f)
        else if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.background.copy(alpha = 0.36f)


//  return  if (isSelected)
//        MaterialTheme.colorScheme.onPrimary
//    else MaterialTheme.colorScheme.primary.copy(alpha = 0.36f)
}

@Composable
fun normalSize(): Modifier {
    return Modifier
        .height(20.dp)
        .width(20.dp)
}

@Composable
fun resize(isSelected: Boolean): Modifier {
    return if (isSelected)
        Modifier
            .height(24.dp)
            .width(24.dp)
            .shadow(elevation = 4.dp)
    else Modifier
}


@Preview
@Composable
fun BottomNavigationBarPreview() {
    YomikazeappkotlinTheme(appTheme = AppTheme.LIGHT) {
        val navController = rememberNavController()
        HomeBottomNavBar(navController)
    }

}
