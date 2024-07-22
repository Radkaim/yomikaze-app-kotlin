package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.ChapterCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.UnlockChapterDialogComponent
import com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.ViewChapterModel


/**
 * List Chapter in Comic Detail View
 */
@Composable
fun ListChapterInComicDetailView(
    comicDetailViewModel: ComicDetailViewModel,
    comicId: Long,
    viewChapterModel: ViewChapterModel = hiltViewModel()
) {
    val context = LocalContext.current
    val appPreference = AppPreference(context)
//    val viewChapterModel = hiltViewModel<ViewChapterModel>()
    val viewChapterState = viewChapterModel.state.collectAsState()
    LaunchedEffect(Unit) {
        comicDetailViewModel.getListChapterByComicId(comicId = comicId)
    }


    var isSelected by remember { mutableStateOf(true) }
    var isReversed by remember { mutableStateOf(false) }

    val listChapter = comicDetailViewModel.state.value.listChapters

    var showDialog by remember { mutableStateOf(false) }
    var selectedChapter by remember { mutableStateOf<Chapter?>(null) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Total Chapter: ${listChapter?.size}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(8.dp)

        )

        SortComponent(
            isOldestSelected = isSelected,
            onOldSortClick = {
                isSelected = true
                isReversed = false
            },
            onnNewSortClick = {
                isSelected = false
                isReversed = true
            }
        )
    }
    //list Chapter
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp, bottom = 15.dp)
            .offset(x = (-4).dp),
        verticalArrangement = Arrangement.spacedBy(4.dp) // 8.dp space between each item
    ) {
        listChapter?.let { // means if listChapter is not null
            val sortedList = if (isReversed) it.reversed() else it
            items(sortedList) { chapter ->
                LaunchedEffect(key1 = viewChapterState.value.isUnlockChapterSuccess) {
                    if (viewChapterState.value.isUnlockChapterSuccess) {
                        comicDetailViewModel.navigateToViewChapter(
                            comicId = comicId,
                            chapterNumber = selectedChapter?.number!!
                        )
                    }
                }
                ChapterCard(
                    chapterNumber = chapter.number,
                    title = chapter.name,
                    views = chapter.views,
                    comments = chapter.comments,
                    publishedDate = chapter.creationTime,
                    isLocked = if (comicDetailViewModel.checkUserIsLogin()) !chapter.isUnlocked else chapter.hasLock,
                    onClick = {
                        if (!comicDetailViewModel.checkUserIsLogin() && chapter.hasLock) {
                            Toast.makeText(
                                context,
                                "Please sign in to unlock this chapter",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            if (if (comicDetailViewModel.checkUserIsLogin()) !chapter.isUnlocked else chapter.hasLock) {
                                selectedChapter = chapter
                                showDialog = true
                            } else {
                                comicDetailViewModel.navigateToViewChapter(
                                    comicId,
                                    chapter.number
                                )
                            }
                        }
                    },
                    onReportClick = {}
                )

            }
        }
    }
    if (showDialog) {
        UnlockChapterDialogComponent(
            title = "Do you want to unlock this chapter?",
            chapterNumber = selectedChapter?.number!!,
            totalCoin = selectedChapter?.price?.toLong() ?: 0,
            coinOfUserAvailable = appPreference.userBalance,
            onConfirmClick = {
                //UnlockUC
                //if(state.success) {navigateToViewChapter}
                viewChapterModel.unlockAChapter(
                    comicId = comicId,
                    chapterNumber = selectedChapter?.number!!,
                    price = selectedChapter?.price?.toLong() ?: 0
                )
            },
            onDismiss = { showDialog = false },
            onBuyCoinsClick = {
                comicDetailViewModel.navigateToCoinShop()
            }
        )
    }
}
