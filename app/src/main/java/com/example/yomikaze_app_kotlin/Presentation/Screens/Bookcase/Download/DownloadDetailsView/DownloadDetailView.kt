package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.Download.NormalChapterDownload
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DownloadDetailView(
    comicId: Long,
    comicName: String,
    navController: NavController,
    downloadDetailViewModel: DownloadDetailViewModel = hiltViewModel()
) {
    downloadDetailViewModel.setNavController(navController)
    val state by downloadDetailViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        downloadDetailViewModel.getChaptersFromDBByComicId(comicId)
    }

    val chapterList = state.listChapterDownloaded

    Scaffold(

        topBar = {
            CustomAppBar(
                title = "$comicName",
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
                actions = {
                    val colorSelected = if (state.isEditMode) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                    Text(
                        text = if (state.isEditMode) "Cancel" else "Edit",
                        color = colorSelected,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            downloadDetailViewModel.toggleEditMode()
                        }
                            .padding(end = 10.dp)
                    )
                },
            )
        },
        bottomBar = {
            // Bottom bar content
            Column {
                Divider(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiary)
                        .clickable {
                            downloadDetailViewModel.navigateToChooseDownloadChapterScreen(
                                comicId,
                                comicName
                            )
                            val selectedChapters = downloadDetailViewModel.getSelectedChapters()
                            Log.d("DownloadDetailView", "Selected Chapters: $selectedChapters")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Downloaded More",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 15.dp)
                    )
                }
            }
        },
    )
    { paddingValues ->
        // Nội dung của bạn ở đây, có thể dùng paddingValues nếu cần

        Column(
            verticalArrangement = Arrangement.spacedBy(150.dp), // 15.dp space between each card
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    start = 4.dp,
                    end = 4.dp,
                    //bottom = 80.dp
                ) // Optional padding for the entire list
                .background(MaterialTheme.colorScheme.background)
                .wrapContentSize(Alignment.Center)
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),// 8.dp space between each item
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
            ) {
                item {
                    chapterList.forEachIndexed() { index, chapter ->
                        NormalChapterDownload(
                            orderIndex = index + 1,
                            chapterNumber = chapter.number,
                            totalMbs = chapter.size,
                            isDownloaded = chapter.isDownloaded,
//                            isInSelectionMode = false,//chapter.isInSelectionMode,
//                            onClicked = {},
                            isInSelectionMode = state.isEditMode,
                            isSelected = chapter.isSelected,
                            onClicked = {
                                if (state.isEditMode) {
                                    downloadDetailViewModel.toggleChapterSelection(index)
                                } else {
                                    downloadDetailViewModel.navigateToViewChapterScreen(
                                        comicId,
                                        chapter.number
                                    )
                                }
                            }
                        )
                    }
                }

            }


        }
    }
}