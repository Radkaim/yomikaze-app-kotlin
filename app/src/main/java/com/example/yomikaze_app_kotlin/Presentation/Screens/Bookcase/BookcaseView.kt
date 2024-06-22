package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.History.HistoryView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.LibraryView
import com.example.yomikaze_app_kotlin.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookcaseView(
    navController: NavController,
    viewModel: BookcaseViewModel = hiltViewModel()
) {

    val tabs = listOf("History", "Library", "Download")
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Bookcase",
                navigationIcon = {},
            )
        })
    {

        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.tertiary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) // Change this to your desired color
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = {
                        Text(
                            text = title,
                            color = changeColor(selectedTabIndex, index),
                            fontWeight = FontWeight.Medium

                        )
                    },
                        selected = selectedTabIndex == index,
                        onClick = { viewModel.setSelectedTabIndex(index) },

                        icon = {
                            setIcon(index, selectedTabIndex)
                        }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> HistoryView(navController = navController)
                1 -> LibraryView()
                2 -> DownloadView(navController = navController)
            }
        }
    }
}

@Composable
fun setIcon(index: Int, tabIndex: Int) {
    return when (index) {
        0 -> {
            Icon(
                painterResource(id = R.drawable.ic_history),
                contentDescription = "History icon",
                tint = changeColor(tabIndex, 0),
                modifier = changeSizeIcon(),
            )
        }

        1 -> {
            Icon(
                painterResource(id = R.drawable.ic_library),
                contentDescription = "Library icon",
                tint = changeColor(tabIndex, 1),
                modifier = changeSizeIcon(),
            )
        }

        2 -> {
            Icon(
                painterResource(id = R.drawable.ic_download),
                contentDescription = "Download icon",
                tint = changeColor(tabIndex, 2),
                modifier = changeSizeIcon(),
            )
        }

        else -> {}
    }
}

@Composable
fun changeSizeIcon(): Modifier {
    return Modifier
        .width(20.dp)
        .height(20.dp)
}

@Composable
fun changeColor(tabIndex: Int, index: Int): Color {
    return if (tabIndex == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary.copy(
        alpha = 0.36f
    )
}
