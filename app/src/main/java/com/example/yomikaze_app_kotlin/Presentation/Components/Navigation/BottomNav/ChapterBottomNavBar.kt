package com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage.FlipPagerOrientation
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.R

@Composable
fun ChapterBottomNavBar(
    navController: NavController,
    appPreference: AppPreference,
    canPrevious: Boolean,
    canNext: Boolean,
    isScrollModeSelected: (Boolean) -> Unit,
    selectedTabIndex: Int,
    setSelectedTabIndex: (Int) -> Unit,
    isFlipModeSelected: (FlipPagerOrientation) -> Unit,
    checkAutoScroll: (Boolean) -> Unit,

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
                BottomChapterNavItems.PreviousChapter -> if (canPrevious) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.2f
                )

                BottomChapterNavItems.NextChapter -> if (canNext) MaterialTheme.colorScheme.secondaryContainer else colorSelected(
                    isSelected
                )

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
                        "previous_chapter_route" -> if (canPrevious) {}
                        "list_chapter_route" -> showDialog = 2
                        "comment_route" -> showDialog = 1
                        "setting_route" -> showDialog = 4
                        "next_chapter_route" -> if (canNext) {}
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
                    SettingDialog(
                        isScrollModeSelected = isScrollModeSelected,
                        onDismissRequest = {
                            showDialog = null
                            currentRoute = null
                        },
                        selectedTabIndex = selectedTabIndex,
                        setSelectedTabIndex = setSelectedTabIndex,
                        isFlipModeSelected = isFlipModeSelected,
                        appPreference = appPreference,
                        checkAutoScroll = checkAutoScroll
                    )
                }

                5 -> {

                }
            }
        }
    }
}

data class ModeSelected(
    val title: String,
    val icon: Int,
)

@Composable
fun SettingDialog(
    isScrollModeSelected: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    selectedTabIndex: Int,
    setSelectedTabIndex: (Int) -> Unit,
    isFlipModeSelected: (FlipPagerOrientation) -> Unit,
    appPreference: AppPreference,
    checkAutoScroll: (Boolean) -> Unit,
) {

    val listModes = listOf(
        ModeSelected("Scroll", R.drawable.ic_plus),
        ModeSelected("Vertical", R.drawable.ic_delete),
        ModeSelected("Horizontal", R.drawable.ic_edit),
    )


    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.7f))
                .clickable { onDismissRequest() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 40.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 20.dp, end = 10.dp)
                    ) {
                        Text(
                            text = "Reading Mode",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 5.dp, end = 40.dp),
                        )
                        listModes.forEachIndexed { index, mode ->
                            SelectedModeComponent(
                                title = mode.title,
                                icon = mode.icon,
                                onModeSelected = {
                                    when (index) {
                                        0 -> {
                                            isScrollModeSelected(true)
                                            setSelectedTabIndex(index)
                                            appPreference.isScrollMode = true
                                            onDismissRequest()
                                        }
                                        1 -> {
                                            isScrollModeSelected(false)
                                            setSelectedTabIndex(index)
                                            isFlipModeSelected(FlipPagerOrientation.Vertical)
                                            appPreference.isScrollMode = false
                                            appPreference.orientation = true
                                        }
                                        2 -> {
                                            isScrollModeSelected(false)
                                            setSelectedTabIndex(index)
                                            isFlipModeSelected(FlipPagerOrientation.Horizontal)
                                            appPreference.isScrollMode = false
                                            appPreference.orientation = false
                                        }
                                    }
                                },
                                isSelected = index == selectedTabIndex
                            )
                        }
                    }
                    Divider(
                        color = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp),
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 20.dp, end = 10.dp)
                    ) {
                        Text(
                            text = "Auto Scroll",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 5.dp, end = 245.dp),
                        )
                        Switch(
                            checked = appPreference.autoScrollChecked,
                            onCheckedChange = {
                                appPreference.autoScrollChecked = it
                                checkAutoScroll(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                                checkedTrackColor = MaterialTheme.colorScheme.inverseOnSurface,
                                uncheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                uncheckedTrackColor = MaterialTheme.colorScheme.inverseOnSurface,
                            ),
                            modifier = Modifier
                                .width(50.dp)
                                .height(10.dp)
                                .padding(end = 15.dp)
                                .scale(0.8f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SelectedModeComponent(
    title: String,
    icon: Int,
    onModeSelected: () -> Unit,
    isSelected: Boolean,
) {

    IconButton(
        onClick = {
            onModeSelected()
        },
        modifier = Modifier
            .shadow(8.dp, RoundedCornerShape(10.dp))
            .width(70.dp)
            .height(30.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.surface.copy(0.2f) else MaterialTheme.colorScheme.secondary.copy(
                    alpha = 0.2f
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.secondary.copy(
                    alpha = 0.2f
                ),
                shape = RoundedCornerShape(10.dp)
            )

    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )

            Icon(
                painterResource(id = icon),
                contentDescription = "Switch Mode",
                tint = Color.White
            )
        }

    }

}