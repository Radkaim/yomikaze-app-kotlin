package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.Download.NormalChapterDownload
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar

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
                    Text(
                        text = "Edit",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            //TODO: Handle Edit Click
                        }
                    )
                },
            )
        }
    )
    { paddingValues ->
        // Nội dung của bạn ở đây, có thể dùng paddingValues nếu cần

        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp), // 15.dp space between each card
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
                            isInSelectionMode = false,//chapter.isInSelectionMode,
                            isSelected = false,//chapter.isSelected,
                            onClicked = {},
                        )
                    }
                }
            }
        }
    }
}