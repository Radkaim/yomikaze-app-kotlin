package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R

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

    if (CheckNetwork()) {
        if (state.isListNumberLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimationComponent(
                    animationFileName = R.raw.loading, // Replace with your animation file name
                    loop = true,
                    autoPlay = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.15f)
                )
            }
        } else {
            ChooseChapterDownloadContent(
                comicId = comicId,
                navController = navController,
                chooseChapterDownloadViewModel = chooseChapterDownloadViewModel,
                state = state
            )
        }
    } else {
        UnNetworkScreen()
    }
//    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChooseChapterDownloadContent(
    comicId: Long,
    navController: NavController,
    chooseChapterDownloadViewModel: ChooseChapterDownloadViewModel,
    state: ChooseChapterDownloadState
) {

    val chapterList = state.listChapterForDownloaded

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
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary)

            ) {
                Divider(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
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
                                chooseChapterDownloadViewModel.selectAllChapters()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        //total selected chapters
                        val totalSelectedChapters =
                            chooseChapterDownloadViewModel.getSelectedChapters().size
                        Text( text = "Have chosen: $totalSelectedChapters ${if (totalSelectedChapters > 1) "chapters" else "chapter"}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.W300,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.align(Alignment.Center).padding(start = 70.dp)
                        )
                    }
                }
                Divider(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
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
                                chooseChapterDownloadViewModel.selectAllChapters()
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
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)

                            )
                            Text(
                                text = "Choose All",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier

                            )
                        }

                    }
                    // Divider between two boxes
                    Divider(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        modifier = Modifier
                            .height(30.dp) // Height of the divider to match the height of the Boxes
                            .width(1.dp)   // Width of the divider
                            .offset(y = 5.dp) // Offset to center the divider vertically
                    )

                    Box(
                        modifier = Modifier
                            .padding(end = 40.dp)
                            .clickable {
//                                chooseChapterDownloadViewModel.getComicDetailsAndDownload(comicId)
                                val se =
                                    chooseChapterDownloadViewModel.getTotalPriceOfSelectedChapters()
                                Log.d("ChooseChapterDownload", "ChooseChapterDownloadContent: $se")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_download),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                            Text(
                                text = "Download",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                            )
                        }
                    }

                }
            }
        }
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total chapters: ${chapterList!!.size}",
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
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp),
            ) {
                items(chapterList.size) { index ->
                    val chapter = chapterList[index]
                    BoxSelectedDownload(
                        chapter = chapter,
                        isSelected = chapter.isSelected,
                        onClicked = { chooseChapterDownloadViewModel.toggleChapterSelection(chapter) }
                    )
                }

            }
        }
    }
}

@Composable
fun BoxSelectedDownload(
    chapter: Chapter,
    isSelected: Boolean,
    onClicked: () -> Unit

) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .width(60.dp)
            .padding(4.dp)
            .background(if (!isSelected) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.background)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            .then(
                if (isSelected) Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                ) else Modifier
            )
            .clickable {
//                if (chapter.hasLock) {
//                   Log.d("ChooseChapterDownload", "You can't download this chapter and you need use $${chapter.price} coin to unlock it")
//                } else {
                onClicked()

            }
    ) {
        // Tick icon in the top-left corner
        if (chapter.isDownloaded) {
            Icon(
                painter = painterResource(id = R.drawable.ic_tick_downloaded),
                contentDescription = "tick downloaded",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .width(15.dp)
                    .height(17.dp)
                    .align(Alignment.TopStart)
                    .padding(start = 4.dp, top = 4.dp)
                    .offset(x = 4.dp)
            )
        }
        if (chapter.hasLock && !chapter.isDownloaded) {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock),
                contentDescription = "lock",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .width(15.dp)
                    .height(17.dp)
                    .align(Alignment.TopStart)
                    .padding(end = 4.dp, top = 4.dp)
                    .offset(x = 4.dp)
            )
        }

        // Chapter number in the center
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = chapter.number.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}