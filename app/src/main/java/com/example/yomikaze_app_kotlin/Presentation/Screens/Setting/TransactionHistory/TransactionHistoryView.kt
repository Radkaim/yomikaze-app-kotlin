package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.TransactionHistory

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.CoinShop.TransactionHistoryCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.CoinShopCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TransactionHistoryView(
    transactionHistoryViewModel: TransactionHistoryViewModel = hiltViewModel(),
    navController: NavController,
) {

    //state
    val state by transactionHistoryViewModel.state.collectAsState()


    //set navController for viewModel
    transactionHistoryViewModel.setNavController(navController)


    if (CheckNetwork()) {
        TransactionHistoryViewContent(
            transactionHistoryViewModel = transactionHistoryViewModel,
            state = state,
            navController = navController,
        )
    } else {
        UnNetworkScreen()
    }

}
//edit profile
//noti
//change passsword
//chapter comment
//report


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TransactionHistoryViewContent(
    transactionHistoryViewModel: TransactionHistoryViewModel,
    state: TransactionHistoryState,
    navController: NavController,
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val page = remember { mutableStateOf(1) }
    val loading = remember { mutableStateOf(false) }
    val listComicByViewRanking = remember { state.listTransactionHistory }

    var isSelected by remember { mutableStateOf(false) }
    var isReversed by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Transaction History",
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
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp), // 15.dp space between each card
            modifier = Modifier
                .padding(
//                    top = 15.dp,
//                    start = 4.dp,
//                    end = 4.dp,
//                    bottom = 4.dp
                ) // Optional padding for the entire list
                .background(MaterialTheme.colorScheme.background)
                .wrapContentSize(Alignment.Center)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 5.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {
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

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // 8.dp space between each item
            ) {

                if (state.isLoadingTransactionHistory) {
                    item {
                        repeat(6) {
                            CoinShopCardShimmerLoading()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                if (!state.isLoadingTransactionHistory && state.listTransactionHistory.isNotEmpty()) {
                    val sortedList = if (isReversed) {
                        state.listTransactionHistory.sortedByDescending { it.createdTime }
                    } else {
                        state.listTransactionHistory.sortedBy { it.createdTime }
                    }
                    items(sortedList) { transactionHistoryResponse ->
                        TransactionHistoryCard(
                            type = transactionHistoryResponse.type,
                            amount = transactionHistoryResponse.amount,
                            createdAt = transactionHistoryResponse.createdTime,
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

        LaunchedEffect(
            key1 = page.value,
        ) {
            Log.d("ComicCommentContent", "page1: ${page.value}")
            if (page.value > state.currentPage.value && !loading.value) {
                loading.value = true
                transactionHistoryViewModel.getTransactionHistory(page.value)
                loading.value = false
            }

        }
        // Sử dụng SideEffect để phát hiện khi người dùng cuộn tới cuối danh sách
        LaunchedEffect(key1 = listState, key2 = page.value) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collectLatest { lastVisibleItemIndex ->
                    if (!loading.value && lastVisibleItemIndex != null && lastVisibleItemIndex >= state.listTransactionHistory.size - 2) {
//                        Log.d(
//                            "ComicCommentContent",
//                            "ComicCommentContent12: ${lastVisibleItemIndex} and ${state.listTransactionHistory.size}"
//                        )
                        if (state.currentPage.value < state.totalPages.value) {
//                            Log.d("HotComicViewModel", "page2: ${state.currentPage.value}")
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
                    if (lastVisibleItemIndex != null && lastVisibleItemIndex == state.listTransactionHistory.size && listComicByViewRanking.size > 5) {
                        if (state.currentPage.value == state.totalPages.value && state.totalPages.value != 0) {
                            Toast.makeText(context, "No transactions left", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
        }
    }
}
