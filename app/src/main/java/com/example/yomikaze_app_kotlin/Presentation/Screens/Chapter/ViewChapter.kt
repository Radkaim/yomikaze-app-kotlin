package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage.FlipPager
import com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage.FlipPagerOrientation
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.ChapterBottomNavBar
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NoNetworkAvailable
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewChapter(
    comicId: Long,
    chapterNumber: Int,
    navController: NavController,
    viewChapterModel: ViewChapterModel = hiltViewModel()
) {
    //state
    val state by viewChapterModel.state.collectAsState()


    //set navController for viewModel
    viewChapterModel.setNavController(navController)

    //get pages by chapter number of comic
    val isNetworkAvailable = CheckNetwork()
    LaunchedEffect(Unit) {
        if (isNetworkAvailable) {
            Log.e("ViewChapter", "Network Available")
            viewChapterModel.getPagesByChapterNumberOfComic(comicId, chapterNumber)
        } else {
            Log.e("ViewChapter", "No Network Available")
            viewChapterModel.getPageByComicIdAndChapterNumberInDB(comicId, chapterNumber)
        }
    }

    if (!isNetworkAvailable && !state.isPagesExistInDB) {
        NoNetworkAvailable()
    } else {
        ViewChapterContent(
            state = state,
            navController = navController,
            chapterNumber = chapterNumber,
            viewChapterModel = viewChapterModel
        )
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewChapterContent(
    state: ViewChapterState,
    navController: NavController,
    chapterNumber: Int,
    viewChapterModel: ViewChapterModel
) {
    val context = LocalContext.current
    val appPreference = AppPreference(context)


//    var isScrollMode by remember {
//        mutableStateOf(true)
//    }
//    // Flip View
//    var orientation: FlipPagerOrientation by remember {
//        mutableStateOf(FlipPagerOrientation.Vertical)
//    }
    var orientation by remember { mutableStateOf(if (appPreference.orientation) FlipPagerOrientation.Vertical else FlipPagerOrientation.Horizontal) }
    var isScrollMode by remember { mutableStateOf(appPreference.isScrollMode) }

//    var autoScroll by remember {
//        mutableStateOf(false)
//    }
    var autoScroll by remember { mutableStateOf(appPreference.autoScrollChecked) }
    val images = state.pagesImage


    val pagerState = rememberPagerState { images.size }

    val scrollState = rememberLazyListState()

    val currentPage = pagerState.currentPage + 1
    val currentPageScroll = scrollState.firstVisibleItemIndex + 1
    val imagePath = if (CheckNetwork()) APIConfig.imageAPIURL.toString() else {
        ""
    }

    LaunchedEffect(autoScroll) {
        if (autoScroll && isScrollMode) {
            while (autoScroll) {
                delay(3000)
                scrollState.animateScrollToItem(scrollState.firstVisibleItemIndex + 1)
            }
        } else if (autoScroll && !isScrollMode) {
            while (autoScroll) {
                delay(3000) // Adjust delay time as needed for auto-flip
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }

    var (selectedTabIndex, setSelectedTabIndex) = remember {
        mutableStateOf(
            when {
                isScrollMode -> 0
                orientation == FlipPagerOrientation.Vertical -> 1
                else -> 2
            }
        )
    }
    Scaffold(
        topBar = {
            CustomAppBar(
                title = state.pageResponse?.name ?: "Chapter $chapterNumber",

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
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(
//                        color = MaterialTheme.colorScheme.surface.copy(0.2f),
//                        shape = RoundedCornerShape(10.dp)
//                    ),
            ) {
                // Add the dynamic title above the bottom bar
                Box(
                    modifier = Modifier
                        .width(70.dp)

                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(0.4f),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Text(
                        text = if (isScrollMode)
                            "($currentPageScroll / ${images.size})"
                        else "($currentPage / ${images.size})",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                // The actual bottom bar
                ChapterBottomNavBar(
                    navController = navController,
                    appPreference = appPreference,
                    canPrevious = true,
                    canNext = true,
                    isScrollModeSelected = { isScrollMode = it },
                    selectedTabIndex = selectedTabIndex,
                    setSelectedTabIndex = setSelectedTabIndex,
                    isFlipModeSelected = {
                        orientation = it
                    },
                    checkAutoScroll = { autoScroll = it },

                    )
            }
        }
    )
    {
        if (isScrollMode) {
            // Scroll View
            // Comic Image
            LazyColumn(
                modifier = Modifier
//                    .fillMaxSize()
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = scrollState

            ) {
                items(images.size) { index ->
                    images[index].let { image ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imagePath + image)
                                .memoryCachePolicy(CachePolicy.ENABLED)
                                .build(),
                            placeholder = painterResource(R.drawable.placeholder),
                            error = painterResource(R.drawable.placeholder),
                            contentDescription = "Page of Chapter Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(700.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                    shape = MaterialTheme.shapes.small
                                )
                                .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
                        )
                    }
                }
            }
        } else {

            FlipPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                orientation = orientation,
            ) { page ->
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                )
                //Comic Image
                val imagePath = if (CheckNetwork()) APIConfig.imageAPIURL.toString() else {
                    ""
                }
                images[page].let { image ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imagePath + image)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        placeholder = painterResource(R.drawable.placeholder),
                        error = painterResource(R.drawable.placeholder),
                        contentDescription = "Page of Chapter Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                shape = MaterialTheme.shapes.small
                            )
                            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
                    )
                }
            }
        }
    }
}




