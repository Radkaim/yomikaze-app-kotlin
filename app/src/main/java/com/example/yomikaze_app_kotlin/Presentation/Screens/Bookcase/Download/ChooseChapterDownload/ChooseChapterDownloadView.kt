package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.shimmerLoadingAnimation
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChooseChapterDownloadView(
    comicId: Long,
    comicName: String,
    navController: NavController,
    chooseChapterDownloadViewModel: ChooseChapterDownloadViewModel = hiltViewModel()
) {
    chooseChapterDownloadViewModel.setNavController(navController)
    val state by chooseChapterDownloadViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        chooseChapterDownloadViewModel.getListChapterByComicId(comicId = comicId)
    }
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Choose Chapter To Download",
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
            )
        })
    {
        if (CheckNetwork()) {
            ChooseChapterDownloadContent(
                comicId = comicId,
                navController = navController,
                chooseChapterDownloadViewModel = chooseChapterDownloadViewModel,
                state = state
            )
        } else {
            UnNetworkScreen()
        }
    }
}

@Composable
fun ChooseChapterDownloadContent(
    comicId: Long,
    navController: NavController,
    chooseChapterDownloadViewModel: ChooseChapterDownloadViewModel,
    state: ChooseChapterDownloadState
) {


    if (state.isListNumberLoading) {
        //Loading
        CircularProgressIndicator()

    } else {
        val chapterNumbers = state.listNumberChapters

        Log.d("ChooseChapterDownloadContent", "ChooseChapterDownloadContent:")
        // Remember the state of selected chapters
        val selectedChapters =
            remember { mutableStateListOf<Boolean>().apply { addAll(List(chapterNumbers!!.size) { false }) } }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total chapters: ${chapterNumbers!!.size}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            var isSelected by remember { mutableStateOf(true) }

            SortComponent(
                isOldestSelected = isSelected,
                onnNewSortClick = { isSelected = false },
                onOldSortClick = { isSelected = true }
            )
        }


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 5.dp, end = 5.dp, top = 50.dp, bottom = 5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                //.padding(start = 5.dp),
            ) {
                items(chapterNumbers!!.size) { index ->
                    val number = chapterNumbers[index]
                    val isSelected = selectedChapters[index]

                    Box(
                        modifier = Modifier.run {
                            padding(5.dp)
                                .background(if (!isSelected) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.background)
                                .toggleable(
                                    value = isSelected,
                                    onValueChange = {
                                        selectedChapters[index] = it
                                    }
                                )
                                .shimmerLoadingAnimation()
                                .height(50.dp)
                                .width(80.dp)
                                .then(
                                    if (isSelected) Modifier.border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.primary
                                    ) else Modifier
                                )

                        }
                    ) {
                        Text(
                            text = number.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

            }

            Button(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    val chaptersToDownload =
                        chapterNumbers!!.filterIndexed { index, _ -> selectedChapters[index] }

//                    chooseChapterDownloadViewModel.getComicDetailsAndDownload(comicId)
//
//                    chooseChapterDownloadViewModel.downloadListChapterChoose(
//                        comicId,
//                        chaptersToDownload
//                    )
                   // chooseChapterDownloadViewModel.downloadAllPageOfChapterFromDB(comicId)
                    chooseChapterDownloadViewModel. getPagesByChapterNumberOfComic(68638295025815553, 1,)
                    Log.d("ChooseChapterDownloadContent", "ChooseChapterDownloadContent: $chaptersToDownload")
//                onDownload(chaptersToDownload)
//                onDismiss()
                }) {
                Text(
                    text = "Download",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            }

        }
    }

}