package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.ChooseChapterDownload

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.UnlockChapterDialogComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.BoxSelectedDownloadShimmerLoading
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
        ChooseChapterDownloadContent(
            comicId = comicId,
            navController = navController,
            chooseChapterDownloadViewModel = chooseChapterDownloadViewModel,
            state = state
        )
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

    val chapterList = state.listChapterForDownloaded.sortedBy { it.number }

    var totalPrice by remember { mutableStateOf(0) }

    val context = LocalContext.current
    val appPreference = AppPreference(context)
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = state.isUnlockChapterSuccess) {
        if (state.isUnlockChapterSuccess) {
            chooseChapterDownloadViewModel.getComicDetailsAndDownload(
                comicId
            )
            Toast
                .makeText(
                    context,
                    "Downloaded successfully, please wait a moment",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
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
                        Text(
                            text = "Have chosen: $totalSelectedChapters ${if (totalSelectedChapters > 1) "chapters" else "chapter"}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.W300,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(start = 70.dp)
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
                                //check if selected chapter contain isLocked = true
                                val selectedChaptersContainLock =
                                    chooseChapterDownloadViewModel.getSelectedChaptersContainIsUnlocked()
                                Log.d(
                                    "ChooseChapterDownload",
                                    "Selected chapters contain lock: $selectedChaptersContainLock"
                                )
                                if (selectedChaptersContainLock.isNotEmpty()) {
                                    if (appPreference.isUserLoggedIn) {
                                        totalPrice =
                                            chooseChapterDownloadViewModel.getTotalPriceOfSelectedChaptersContainLock()
                                        showDialog = true
                                    } else {
                                        Toast
                                            .makeText(
                                                context,
                                                "You need to sign in to unlock this chapter",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        return@clickable
                                    }
                                }
                                if (chooseChapterDownloadViewModel
                                        .getSelectedChapters()
                                        .isNotEmpty() && !chooseChapterDownloadViewModel.checkSelectedChaptersContainLockedChapter()
                                // khi chon chaptyer ddang khoa vaanx hienej toast download
                                // thanh cong check laij trang thai download cos khoa view chaopter ddc mawcj duf pending
                                ) {
                                    chooseChapterDownloadViewModel.getComicDetailsAndDownload(
                                        comicId
                                    )
                                    Toast
                                        .makeText(
                                            context,
                                            "Downloaded successfully, please wait a moment",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
//                                else {
//                                    Toast
//                                        .makeText(
//                                            context,
//                                            "You need to sign in to unlock this chapter",
//                                            Toast.LENGTH_SHORT
//                                        )
//                                        .show()
//                                    return@clickable
//                                }
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

        var isSelected by remember { mutableStateOf(true) }
        var isReversed by remember { mutableStateOf(false) }
        if (state.isListNumberLoading) {
            val item = 1..12
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(bottom = 50.dp),
            ) {
                items(item.count()) { index ->
                    BoxSelectedDownloadShimmerLoading()
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total chapters: ${chapterList!!.size}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    SortComponent(
                        isOldestSelected = isSelected,
                        onnNewSortClick = {
                            isSelected = false
                            isReversed = true
                        },
                        onOldSortClick = {
                            isSelected = true
                            isReversed = false
                        }
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 50.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 50.dp),
                ) {
                    chapterList?.let {
                        val sortedList = if (isReversed) it.reversed() else it
                        items(chapterList.size) { index ->
                            val chapter = sortedList[index]
                            BoxSelectedDownload(
                                chapter = chapter,
                                isSelected = chapter.isSelected,
                                appPreference = appPreference,
                                onClicked = {
                                    chooseChapterDownloadViewModel.toggleChapterSelection(
                                        chapter
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
        if (showDialog) {
            UnlockChapterDialogComponent(
                title = "You need to unlock ${if (chooseChapterDownloadViewModel.getChapterNumberOfSelectedChaptersContainingLockedChapter().size > 1) "these chapters" else "this chapter"}",
                totalCoin = totalPrice.toLong(),
                coinOfUserAvailable = appPreference.userBalance,
                onConfirmClick = {
                    //UnlockUC
                    //if(state.success) {navigateToViewChapter}
                    chooseChapterDownloadViewModel.unlockManyChapters(
                        comicId = comicId,
                        listChapterNumbers = chooseChapterDownloadViewModel.getChapterNumberOfSelectedChaptersContainingLockedChapter(),
                        totalPrice = totalPrice
                    )
                },
                onDismiss = { showDialog = false },
                onBuyCoinsClick = {
                    //navigateToBuyCoins
                    chooseChapterDownloadViewModel.navigateToCoinShop()
                }
            )
        }
    }
}

@Composable
fun BoxSelectedDownload(
    chapter: Chapter,
    isSelected: Boolean,
    appPreference: AppPreference,
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
        if (if (appPreference.isUserLoggedIn) !chapter.isUnlocked else chapter.hasLock && !chapter.isDownloaded) {
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

        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = chapter.number.toString(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}