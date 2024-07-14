package com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav

import android.util.Log
import androidx.compose.foundation.layout.offset
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NetworkDisconnectedDialog

@Composable
fun ChapterBottomNavBar(
    navController: NavController,
    canPrevious: Boolean,
    canNext: Boolean
) {

    val items = listOf(
        BottomChapterNavItems.PreviousChapter,
        BottomChapterNavItems.ListChapter,
        BottomChapterNavItems.Comment,
        BottomChapterNavItems.Setting,
        BottomChapterNavItems.NextChapter
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.primary,
        elevation = 8.dp
    ) {

        var currentRoute by remember { mutableStateOf<String?>(null) }
        var showPopupMenu by remember { mutableStateOf(false) }
        var showDialog by remember { mutableStateOf<Int?>(null) }
        items.forEach { item ->
            val isSelected = currentRoute == item.screen_route
            val tint = when (item) {
                BottomChapterNavItems.PreviousChapter -> if (canPrevious) colorSelected(isSelected) else Color.Gray
                BottomChapterNavItems.NextChapter -> if (canNext) Color.Yellow else colorSelected(isSelected)
                else -> colorSelected(isSelected)
            }
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = tint,
                        modifier = normalSize().then(resize(isSelected))
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = if (isSelected) 11.sp else 10.sp,
                        color = tint,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.36f),
                alwaysShowLabel = true,
                selected = isSelected,
                onClick = {
                    if (currentRoute == item.screen_route) {
                        currentRoute = null
                    } else {
                        currentRoute = item.screen_route
                    }

                    when (item.screen_route) {
                        "previous_chapter_route" -> showDialog = 1
                        "list_chapter_route" -> showDialog = 2
                        "comment_route" -> showDialog = 1
                        "setting_route" -> showDialog = 4
                        "next_chapter_route" -> showDialog = 5
                    }
                    Log.d("ChapterBottomNavBar", "onClick: ${item.screen_route}")
                },
                modifier = if (isSelected) Modifier.offset(y = (-2).dp) else Modifier
            )
        }
        if (showDialog != null) {
            when (showDialog) {
                1 -> {

                   // currentRoute = null
                    NetworkDisconnectedDialog()
                }

                2 -> {
//                    DeleteConfirmDialogComponent(
//                        key = categoryId,
//                        value = value,
//                        title = "Are you sure you want to delete this category?",
//                        onDismiss = { showDialog = 0 },
//                        viewModel = libraryViewModel
//                    )

                }

                3 -> {

                }

                4 -> {

                }

                5 -> {

                }
            }
        }
    }
}

@Composable
fun SettingDialog() {

}

