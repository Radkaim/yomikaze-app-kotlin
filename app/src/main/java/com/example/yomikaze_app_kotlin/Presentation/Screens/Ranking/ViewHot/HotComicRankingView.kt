package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewHot

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.RankingComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.NormalComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HotComicView(
    hotComicViewModel: HotComicRankingViewModel = hiltViewModel(),
    navController: NavController,
) {

    //state
    val state by hotComicViewModel.state.collectAsState()


    //set navController for viewModel
    hotComicViewModel.setNavController(navController)


    if (CheckNetwork()) {
        HotComicViewContent(
            hotComicViewModel = hotComicViewModel,
            state = state,
        )
    } else {
        UnNetworkScreen()
    }

}

@Composable
fun HotComicViewContent(
    hotComicViewModel: HotComicRankingViewModel,
    state: HotComicRankingState,
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val page = remember { mutableStateOf(1) }
    val loading = remember { mutableStateOf(false) }
    val listComicByViewRanking = remember { state.listComicByViewRanking }
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp), // 15.dp space between each card
        modifier = Modifier
            .padding(
                top = 15.dp,
                start = 4.dp,
                end = 4.dp,
                bottom = 4.dp
            ) // Optional padding for the entire list
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize(Alignment.Center)
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp) // 8.dp space between each item
        ) {

            if (state.isLoadingHot) {
                item {
                    repeat(6) {
                        NormalComicCardShimmerLoading()
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
            if (!state.isLoadingHot && state.listComicByViewRanking.isNotEmpty()) {
                itemsIndexed(state.listComicByViewRanking) { index, comic ->
                    RankingComicCard(
                        comicId = comic.comicId,
                        rankingNumber = index + 1,
                        image = APIConfig.imageAPIURL.toString() + comic.cover,
                        comicName = comic.name,
                        status = comic.status,
                        authorNames = comic.authors,
                        publishedDate = comic.publicationDate,
                        ratingScore = comic.averageRating,
                        follows = comic.follows,
                        views = comic.views,
                        comments = comic.comments,
                        backgroundColor = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier,
                        onClicked = { hotComicViewModel.navigateToComicDetail(comic.comicId) }
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
            hotComicViewModel.getComicByViewRanking(page.value)
            loading.value = false
        }

    }
    // Sử dụng SideEffect để phát hiện khi người dùng cuộn tới cuối danh sách
    LaunchedEffect(key1 = listState, key2 = page.value) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastVisibleItemIndex ->
                if (!loading.value && lastVisibleItemIndex != null && lastVisibleItemIndex >= state.listComicByViewRanking.size - 2) {
                    Log.d(
                        "ComicCommentContent",
                        "ComicCommentContent12: ${lastVisibleItemIndex} and ${state.listComicByViewRanking.size}"
                    )
                    if (state.currentPage.value < state.totalPages.value) {
                        Log.d("HotComicViewModel", "page2: ${state.currentPage.value}")
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
                if (lastVisibleItemIndex != null && lastVisibleItemIndex == state.listComicByViewRanking.size && listComicByViewRanking.size > 5) {
                    if (state.currentPage.value == state.totalPages.value && state.totalPages.value != 0) {
                        Toast.makeText(context, "No comics left", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
