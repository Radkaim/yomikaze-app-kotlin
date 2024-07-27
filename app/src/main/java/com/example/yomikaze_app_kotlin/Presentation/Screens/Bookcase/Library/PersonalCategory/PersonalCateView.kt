package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.PersonalCategory

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.ComicCateCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.NormalComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonalCateView(
    categoryId: Long,
    categoryName: String,
    navController: NavController,
    personalCategoryViewModel: PersonalCategoryViewModel = hiltViewModel()
) {
    val state by personalCategoryViewModel.state.collectAsState()

    personalCategoryViewModel.setNavController(navController)



    Scaffold(
        topBar = {
            CustomAppBar(
                title = categoryName,
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
            PersonalCategoryViewContent(
                categoryId = categoryId,
                categoryName = categoryName,
                personalCategoryViewModel = personalCategoryViewModel,
                state = state
            )
        } else {
            UnNetworkScreen()
        }

    }

}

@Composable
fun PersonalCategoryViewContent(
    categoryId: Long,
    categoryName: String,
    personalCategoryViewModel: PersonalCategoryViewModel,
    state: PersonalCategoryState
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val page = remember { mutableStateOf(1) }
    val loading = remember { mutableStateOf(false) }
    

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
            verticalArrangement = Arrangement.spacedBy(8.dp), // 8.dp space between each item
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {

            if (state.isLoadingComicCate) {
                item {
                    repeat(6) {
                        NormalComicCardShimmerLoading()
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            itemsIndexed(state.listComics) { index, comic ->
                ComicCateCard(
                    comicId = comic.libraryEntry.comicId,
                    image = APIConfig.imageAPIURL.toString() + comic.libraryEntry.cover,
                    comicName = comic.libraryEntry.name,
                    status = comic.libraryEntry.status,
                    authorNames = comic.libraryEntry.authors,
                    publishedDate = comic.libraryEntry.publicationDate,
                    isDeleted = true,
                    onDeleteClicked = {personalCategoryViewModel.removeComicFromCategory(comic.libraryEntry.comicId, listOf(categoryId))},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(119.dp)
                        .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.medium)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                            shape = MaterialTheme.shapes.small
                        ),
                    onClicked = { personalCategoryViewModel.navigateToComicDetail(comic.libraryEntry.comicId) }
                )
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
        //key2 = state.totalPages
    ) {
        if (page.value > state.currentPage.value && !loading.value) {
            loading.value = true
            personalCategoryViewModel.getComicsInCate(page.value, categoryId = categoryId)
            loading.value = false
        }

    }
    // Sử dụng SideEffect để phát hiện khi người dùng cuộn tới cuối danh sách
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastVisibleItemIndex ->
                if (!loading.value && lastVisibleItemIndex != null && lastVisibleItemIndex >= state.listComics.size - 2) {
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
                if (lastVisibleItemIndex != null && lastVisibleItemIndex == state.listComics.size && state.listComics.size > 5) {
                    if (state.currentPage.value == state.totalPages.value && state.totalPages.value != 0) {
                        Toast.makeText(context, "No Comic More", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}