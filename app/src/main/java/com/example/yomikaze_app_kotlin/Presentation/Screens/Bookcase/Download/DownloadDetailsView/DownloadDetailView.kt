package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.Download.NormalChapterDownload
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R

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
                        modifier = Modifier
                            .clickable {
                                downloadDetailViewModel.toggleEditMode()
                            }
                            .padding(end = 10.dp)
                    )
                },
            )
        },
        bottomBar = {
            // Bottom bar content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.tertiary)

            ) {
                Divider(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
                if (state.isEditMode) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(start = 40.dp)
                                .clickable {
                                   downloadDetailViewModel.selectAllChapters()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.padding(top = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_choose_circle_tick),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)

                                )
                                Text(
                                    text = "Choose All",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight= FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier

                                )
                            }

                        }
                        // Divider between two boxes
                        Divider(
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                            modifier = Modifier
                                .height(40.dp) // Height of the divider to match the height of the Boxes
                                .width(1.dp)   // Width of the divider
                                .offset(y = 5.dp) // Offset to center the divider vertically
                        )

                        Box(
                            modifier = Modifier
                                .padding(end = 40.dp)
                                .clickable {
                                    //  downloadDetailViewModel.deleteSelectedChapters()
//                                    val selectedChapters =
//                                        downloadDetailViewModel.getSelectedChapters()
//                                    Log.d(
//                                        "DownloadDetailView",
//                                        "Selected Chapters: $selectedChapters"
//                                    )
                                    downloadDetailViewModel.deleteAllSelectedChaptersAndPages()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.padding(top = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_delete),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                                Text(
                                    text = "Delete",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight= FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier
                                )
                            }
                        }

                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                downloadDetailViewModel.navigateToChooseDownloadChapterScreen(
                                    comicId,
                                    comicName
                                )
                                val selectedChapters =
                                    downloadDetailViewModel.getSelectedChapters()
                                Log.d(
                                    "DownloadDetailView",
                                    "Selected Chapters: $selectedChapters"
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Download More",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight= FontWeight.Medium,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 20.dp)
                        )
                    }
                }
            }
        }) { paddingValues ->
        // Nội dung của bạn ở đây, có thể dùng paddingValues nếu cần

        Column(
            verticalArrangement = Arrangement.spacedBy(150.dp), // 15.dp space between each card
            modifier = Modifier
                .padding(
//                    top = 20.dp,
//                    start = 4.dp,
//                    end = 4.dp,
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