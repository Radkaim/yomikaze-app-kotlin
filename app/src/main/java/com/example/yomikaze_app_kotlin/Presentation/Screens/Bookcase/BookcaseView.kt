package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.History.HistoryView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.LibraryView
import com.example.yomikaze_app_kotlin.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookcaseView(initialTab: Int ) {
    var tabIndex by remember { mutableStateOf(initialTab) }

    val tabs = listOf("History", "Library", "Download")

    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "BookCase",
                navigationIcon = {},
            )
        })
    {

        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = MaterialTheme.colorScheme.tertiary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) // Change this to your desired color
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = {
                        Text(
                            text = title,
                            color = changeColor(tabIndex, index),
                            fontWeight = FontWeight.Medium

                        )
                    },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },

                        icon = {
                            when (index) {
                                0 -> {
                                    Icon(
                                        painterResource(id = R.drawable.ic_history),
                                        contentDescription = "History icon",
                                        tint = changeColor(tabIndex, 0)
                                    )
                                }

                                1 -> {
                                    Icon(
                                        painterResource(id = R.drawable.ic_library),
                                        contentDescription = "Library icon",
                                        tint = changeColor(tabIndex, 1)
                                    )
                                }

                                2 -> {
                                    Icon(
                                        painterResource(id = R.drawable.ic_download),
                                        contentDescription = "Download icon",
                                        tint = changeColor(tabIndex, 2)
                                    )
                                }
                            }
                        }
                    )
                }
            }
            when (tabIndex) {
                0 -> HistoryView()
                1 -> LibraryView()
                2 -> DownloadView()
            }
        }
    }
}


@Composable
fun changeColor(tabIndex: Int, index: Int): Color {
    return if (tabIndex == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary.copy(
        alpha = 0.36f
    )
}

@Preview
@Composable
fun BookcasePreview() {
    BookcaseView( initialTab =  0)
}