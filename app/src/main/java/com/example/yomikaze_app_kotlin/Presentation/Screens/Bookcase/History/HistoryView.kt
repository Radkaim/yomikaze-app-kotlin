package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.History

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard.BookcaseComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.DeleteConfirmDialogComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.NotSignIn.NotSignIn
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.NormalComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.R
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HistoryView(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by historyViewModel.state.collectAsState()
    historyViewModel.setNavController(navController)


    if (CheckNetwork()) {
        if (historyViewModel.checkUserIsLogin()) {
            showListHistories(historyViewModel = historyViewModel, state = state)
        } else {
            NotSignIn(navController = navController)
        }
    } else {
        UnNetworkScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryContent(
    historyViewModel: HistoryViewModel,
    state: HistoryState
) {
    var isSelected by remember { mutableStateOf(true) }
    LaunchedEffect(Unit)
    {
        historyViewModel.getHistories(1)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp), // 15.dp space between each card
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 15.dp)
            .padding(bottom = 60.dp) // for show all content
    ) {
        // consume the NormalComicCard composable
        stickyHeader {
            var showPopupMenu by remember { mutableStateOf(false) }
            var showDialog by remember { mutableStateOf<Int?>(null) }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    modifier = Modifier
                        .width(120.dp)
                        .height(30.dp)
                        .padding(start = 4.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(15.dp))
                        .clip(RoundedCornerShape(20.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    onClick = {
                        //TODO
                        showPopupMenu = true
                        showDialog = 1
                        // Log.d("HistoryView", "Clear All")
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "delete_all_history",
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Clear All",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                SortComponent(
                    isOldestSelected = isSelected,
                    onnNewSortClick = { isSelected = false },
                    onOldSortClick = { isSelected = true }
                )
            }
            if (showPopupMenu != null) {
                //TODO
                when (showDialog) {
                    1 -> DeleteConfirmDialogComponent(
                        key = 0, // not use
                        value = "", // not use
                        isDeleteAll = true,
                        title = "Are you sure you want to delete all history record?",
                        onDismiss = { showDialog = 0 },
                        viewModel = historyViewModel!!
                    )
                }
            }
        }


        item {
            if (state.isHistoryListLoading) {
                repeat(4) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        NormalComicCardShimmerLoading()
                    }
                }
            }
        }

        items(state.listHistoryRecords) { historyRecord ->
            BookcaseComicCard(
                comicId = historyRecord.comicId,
                historyRecordId = historyRecord.id,
                value = "Last Chapter: ${historyRecord.chapter.number}", //for delete
                image = APIConfig.imageAPIURL.toString() + historyRecord.comic.cover,
                comicName = historyRecord.comic.name,
                status = historyRecord.comic.status,
                authorNames = historyRecord.comic.authors,
                isHistory = true,
                isDeleted = true,
                lastChapter = historyRecord.chapter.number.toString(),
                publishedDate = historyRecord.comic.publicationDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(119.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                        shape = MaterialTheme.shapes.small
                    ),
                onClicked = {
                    historyViewModel.onHistoryComicClicked(
                        historyRecord.comic.comicId,
                        historyRecord.chapter.number,
                        historyRecord.pageNumber
                    )
                },
                onDeleteClicked = {
                    //   historyViewModel.deleteHistoryRecord(historyRecord.id)
                    //  Log.d("HistoryView", "Delete ${historyRecord.id}")
                },
                historyViewModel = historyViewModel
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun showListHistories(
    historyViewModel: HistoryViewModel,
    state: HistoryState,
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val page = remember { mutableStateOf(1) }
    val loading = remember { mutableStateOf(false) }


    var isSelected by remember { mutableStateOf(true) }
    var isReversed by remember { mutableStateOf(true) }
    // Lưu vị trí hiện tại trước khi thay đổi
    val lastVisibleItemIndex = remember { mutableStateOf(-1) }
//    LaunchedEffect(Unit){
//        historyViewModel.getHistories()
//    }

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp), // 15.dp space between each card
        modifier = Modifier
            .padding(
//                top = 15.dp,
//                start = 4.dp,
//                end = 4.dp,
//                bottom = 4.dp
            ) // Optional padding for the entire list
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize(Alignment.Center)
            .fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp), // 8.dp space between each item
            modifier = Modifier.padding(top = 10.dp, bottom = 60.dp)
        ) {

            stickyHeader {
                var showPopupMenu by remember { mutableStateOf(false) }
                var showDialog by remember { mutableStateOf<Int?>(null) }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        modifier = Modifier
                            .width(120.dp)
                            .height(30.dp)
                            .padding(start = 4.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(15.dp))
                            .clip(RoundedCornerShape(20.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ),
                        onClick = {
                            //TODO
                            showPopupMenu = true
                            showDialog = 1
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "delete_all_history",
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Clear All",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }

                    SortComponent(
                        isOldestSelected = isSelected,
                        onOldSortClick = {
                            isSelected = true
                            isReversed = false
                            lastVisibleItemIndex.value = listState.firstVisibleItemIndex

                        },
                        onnNewSortClick = {
                            isSelected = false
                            isReversed = true
                            lastVisibleItemIndex.value = listState.firstVisibleItemIndex

                        }
                    )
                }
                if (showPopupMenu != null) {
                    //TODO
                    when (showDialog) {
                        1 -> DeleteConfirmDialogComponent(
                            key = 0, // not use
                            value = "", // not use
                            isDeleteAll = true,
                            title = "Are you sure you want to delete all history record?",
                            onDismiss = { showDialog = 0 },
                            viewModel = historyViewModel!!
                        )
                    }
                }
            }

            item {
                if (state.isHistoryListLoading) {
                    repeat(4) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            NormalComicCardShimmerLoading()
                        }
                    }
                }
            }
            if (!state.isHistoryListLoading && state.listHistoryRecords.isNotEmpty()) {
                val sortList = if (isReversed) {
                    state.listHistoryRecords.sortedByDescending { it.creationTime }
                } else {
                    state.listHistoryRecords.sortedBy { it.creationTime }
                }
                items(sortList) { historyRecord ->
                    BookcaseComicCard(
                        comicId = historyRecord.comicId,
                        historyRecordId = historyRecord.id,
                        value = "Last Chapter: ${historyRecord.chapter.number}", //for delete
                        image = APIConfig.imageAPIURL.toString() + historyRecord.comic.cover,
                        comicName = historyRecord.comic.name,
                        status = historyRecord.comic.status,
                        authorNames = historyRecord.comic.authors,
                        isHistory = true,
                        isDeleted = true,
                        lastChapter = historyRecord.chapter.number.toString(),
                        atPageNumber = historyRecord.pageNumber,
                        publishedDate = historyRecord.comic.publicationDate,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(119.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                                shape = MaterialTheme.shapes.small
                            ),
                        onClicked = {
                            historyViewModel.onHistoryComicClicked(
                                historyRecord.comic.comicId,
                                historyRecord.chapter.number,
                                historyRecord.pageNumber
                            )
                        },
                        onDeleteClicked = {
                            //   historyViewModel.deleteHistoryRecord(historyRecord.id)
                            //  Log.d("HistoryView", "Delete ${historyRecord.id}")
                        },
                        historyViewModel = historyViewModel
                    )
                }
            }

            // Hiển thị một mục tải dữ liệu khi cần
            item {
                if (loading.value) {
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
                }
            }
        }
    }

    // Khôi phục vị trí cuộn sau khi thay đổi
    LaunchedEffect(key1 = lastVisibleItemIndex.value) {
        if (lastVisibleItemIndex.value != -1) {
            listState.scrollToItem(lastVisibleItemIndex.value)
        }
    }


    LaunchedEffect(
        key1 = page.value,
        key2 = state,
        key3 = isReversed,
    ) {
//        Log.d("HistoryView", "page1: ${page.value}")
        if (page.value > state.currentPage.value && !loading.value) {
            loading.value = true
//            if (state.listHistoryRecords.isEmpty() && state.currentPage.value >2) {
//            historyViewModel.getHistories(page.value)
//            } else {
            Log.d("HistoryView", "page2: ${page.value}")
            historyViewModel.getHistories(
                page = page.value,
                orderBy =  if (isReversed) "LastModifiedDesc" else "LastModified"
            )
            loading.value = false
        }

    }
    // Sử dụng SideEffect để phát hiện khi người dùng cuộn tới cuối danh sách
    LaunchedEffect(key1 = listState, key2 = page.value) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastVisibleItemIndex ->
                if (!loading.value && lastVisibleItemIndex != null && lastVisibleItemIndex >= state.listHistoryRecords.size - 2) {
                    if (state.currentPage.value < state.totalPages.value) {

                        page.value++
                    }
                }
            }
    }

    //make toast when reach the end of list
    LaunchedEffect(
        key1 = state.currentPage.value,
        key2 = state.totalPages.value,
        key3 = listState
    ) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastVisibleItemIndex ->
                if (lastVisibleItemIndex != null && lastVisibleItemIndex == state.listHistoryRecords.size && state.listHistoryRecords.size > 5) {
                    if (state.currentPage.value == state.totalPages.value && state.totalPages.value != 0) {
                        Toast.makeText(context, "No history records left", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }
}