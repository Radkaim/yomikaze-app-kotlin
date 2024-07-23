package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.UnlockChapterDialogComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage.FlipPager
import com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage.FlipPagerOrientation
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.ChapterBottomNavBar
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewChapter(
    comicId: Long,
    chapterNumber: Int,
    lastPageNumber: Int,
    navController: NavController,
    viewChapterModel: ViewChapterModel = hiltViewModel()
) {
    //state
    val state by viewChapterModel.state.collectAsState()


    //set navController for viewModel
    viewChapterModel.setNavController(navController)

    //get pages by chapter number of comic
    var isNetworkAvailable by remember {
        mutableStateOf(true)
    }
    isNetworkAvailable = CheckNetwork()
    LaunchedEffect(Unit) {
        if (isNetworkAvailable) {
            Log.e("ViewChapter", "Network Available")
            viewChapterModel.getPagesByChapterNumberOfComic(comicId, chapterNumber)
        } else {
            Log.e("ViewChapter", "No Network Available")
            viewChapterModel.getPageByComicIdAndChapterNumberInDB(comicId, chapterNumber)
        }
    }

//    if (isNetworkAvailable) {

        ViewChapterContent(
            comicId = comicId,
            chapterNumber = chapterNumber,
            lastPageNumber = lastPageNumber,
            state = state,
            navController = navController,
            viewChapterModel = viewChapterModel
        )
//    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewChapterContent(
    comicId: Long,
    chapterNumber: Int,
    lastPageNumber: Int,
    state: ViewChapterState,
    navController: NavController,
    viewChapterModel: ViewChapterModel
) {
    val context = LocalContext.current
    val appPreference = AppPreference(context)


    if (CheckNetwork()) {
        LaunchedEffect(Unit) {
            viewChapterModel.getListChapterByComicId(comicId = comicId)
        }
    } else {
        LaunchedEffect(Unit) {
            viewChapterModel.getChaptersFromDBByComicId(comicId = comicId)
        }
    }
    var canGoToPreviousChapter by remember {
        mutableStateOf(false)
    }
    var canGoToNextChapter by remember {
        mutableStateOf(false)
    }

    if (CheckNetwork()) {
        //online mode
        LaunchedEffect(key1 = state.isGetPageApiSuccess) {
            canGoToPreviousChapter =
                viewChapterModel.canGoToPreviousChapter(state.currentChapterNumber)
            canGoToNextChapter = viewChapterModel.canGoToNextChapter(state.currentChapterNumber)
        }
    } else {
        //offline mode
        LaunchedEffect(key1 = state.pagesImage) {
            canGoToPreviousChapter =
                viewChapterModel.canGoToPreviousChapter(state.currentChapterNumber)
            canGoToNextChapter = viewChapterModel.canGoToNextChapter(state.currentChapterNumber)
        }
    }

    var showDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = state.isChapterNeedToUnlock) {
        if (state.isChapterNeedToUnlock) {
            showDialog = true
        }
    }


    LaunchedEffect(key1 = state.isUnlockChapterSuccess) {
        if (state.isUnlockChapterSuccess) {
            viewChapterModel.getPagesByChapterNumberOfComic(comicId, state.chapterUnlockNumber)
            viewChapterModel.resetChapterUnlockNumberAndIsChapterNeedToUnlock()
        }
    }

    LaunchedEffect(key1 = state.isUserNeedToLogin) {
        if (state.isUserNeedToLogin) {
            Toast.makeText(context, "Please sign in  to unlock this chapter", Toast.LENGTH_SHORT)
                .show()
        }
    }

//    var isScrollMode by remember {
//        mutableStateOf(true)
//    }
//    // Flip View
//    var orientation: FlipPagerOrientation by remember {
//        mutableStateOf(FlipPagerOrientation.Vertical)
//    }
    var orientation by remember { mutableStateOf(if (appPreference.orientation) FlipPagerOrientation.Vertical else FlipPagerOrientation.Horizontal) }
    var isScrollMode by remember { mutableStateOf(appPreference.isScrollMode) }


    var autoScroll by remember { mutableStateOf(appPreference.autoScrollChecked) }
    val images = state.pagesImage


    val pagerState = rememberPagerState {images.size}

    val scrollState = rememberLazyListState()



    val currentPageFlip = pagerState.currentPage + 1

    val currentPageScroll = scrollState.firstVisibleItemIndex + 1
    var imagePath = if (CheckNetwork()) APIConfig.imageAPIURL.toString() else {
        ""
    }
    var finalLastPageNumber by remember {
        mutableStateOf(lastPageNumber)
    }

    var currentPage by remember { mutableStateOf(if (isScrollMode) currentPageScroll else currentPageFlip) }
//    LaunchedEffect(key1 = isScrollMode) {
//        currentPage = if (isScrollMode) scrollState.firstVisibleItemIndex else pagerState.currentPage
//    }

    LaunchedEffect(key1 = lastPageNumber, key2 = state.isGetPageApiSuccess, key3= isScrollMode) {
        if (finalLastPageNumber != 0 && state.isGetPageApiSuccess) {
            Log.e("ViewChapterContent", "Last Page Number1: $finalLastPageNumber")

            if (isScrollMode) {
                delay(200)
                scrollState.animateScrollToItem(lastPageNumber - 1)
                finalLastPageNumber = 0
                Log.e("ViewChapterContent", "Last Page Number2: $finalLastPageNumber")
            } else {
                delay(200)
                pagerState.animateScrollToPage(lastPageNumber - 1)
                finalLastPageNumber = 0
                Log.e("ViewChapterContent", "Last Page Number3: $finalLastPageNumber")
            }
        }
    }

    // upatate sroll page to current of another reading mode
    LaunchedEffect(key1 = isScrollMode, key2 = currentPage) {
        if (isScrollMode) {
            currentPage = currentPageFlip
          scrollState.animateScrollToItem(currentPage - 1)
            Log.e("ViewChapterContent", "Current Page: $currentPage")
        } else {
            currentPage = currentPageScroll
            pagerState.animateScrollToPage(currentPage - 1)
            Log.e("ViewChapterContent", "Current Page: $currentPage")
        }
    }


//    LaunchedEffect(key1 = state.isGetPageApiSuccess){
//        if (state.isGetPageApiSuccess && lastPageNumber == 0) {
//            if (isScrollMode) {
//                Log.e("ViewChapterContent", "Last Page Number: $lastPageNumber")
//                scrollState.animateScrollToItem(0)
//            } else {
//                pagerState.animateScrollToPage(0)
//            }
//
//        }
//    }

    LaunchedEffect(key1 = autoScroll) {
        if (autoScroll && isScrollMode) {
            while (autoScroll) {
                delay(3000)
                scrollState.animateScrollToItem(lastPageNumber + 1)
            }
        } else if (autoScroll && !isScrollMode) {
            while (autoScroll) {
                delay(3000) // Adjust delay time as needed for auto-flip
                pagerState.animateScrollToPage(lastPageNumber + 1)
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

    var updateLast by remember {
        mutableStateOf(false)
    }
//    LaunchedEffect(key1 = state.isGetPageApiSuccess) {
//        if (state.isGetPageApiSuccess) {
//            viewChapterModel.updateLastReadPage(
//                comicId = comicId,
//                chapterNumber = state.currentChapterNumber,
//                page = if (isScrollMode) currentPageScroll else currentPageFlip
//            )
//        }
//    }
    val isNetworkAvailable = CheckNetwork()
    Scaffold(
        topBar = {
            CustomAppBar(
                title = state.pageResponse?.name ?: "Chapter $chapterNumber",

                navigationIcon = {
                    IconButton(onClick = {
//                        viewChapterModel.resetStateIsGetPageApiSuccess()
                        if (isNetworkAvailable) {
                            viewChapterModel.updateLastReadPage(
                                comicId = comicId,
                                chapterNumber = state.currentChapterNumber,
                                page = if (isScrollMode) currentPageScroll else currentPageFlip
                            )
//                        Log.e("ViewChapterContent", "Last Read Page: $currentPageScroll")
                            viewChapterModel.resetState()

                            navController.navigate("comic_detail_route/${comicId}") {
                                popUpTo("view_chapter_route/${comicId}/${chapterNumber}") {
                                    inclusive = false
                                }
                            }
                        }else{
                            //navigate to comic detail offline
                            navController.popBackStack()
                        }

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
                        else "($currentPageFlip / ${images.size})",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                // The actual bottom bar
                ChapterBottomNavBar(
                    comicId = comicId,
                    navController = navController,
                    appPreference = appPreference,
                    canPrevious = canGoToPreviousChapter,
                    canNext = canGoToNextChapter,
                    isScrollModeSelected = { isScrollMode = it },
                    selectedTabIndex = selectedTabIndex,
                    setSelectedTabIndex = setSelectedTabIndex,
                    isFlipModeSelected = {
                        orientation = it
                    },
                    checkAutoScroll = { autoScroll = it },
                    viewChapterModel = viewChapterModel,
                    onUpdateClick = {
                        viewChapterModel.updateLastReadPage(
                            comicId = comicId,
                            chapterNumber = state.currentChapterNumber,
                            page = if (isScrollMode) currentPageScroll else currentPageFlip
                        )
                    }
                )
            }
        }
    )
    {
        when {
            showDialog -> {
                UnlockChapterDialogComponent(
                    title = "Do you want to unlock this chapter?",
                    chapterNumber = state.chapterUnlockNumber,
                    totalCoin = 100,
                    coinOfUserAvailable = appPreference.userBalance ?: 0,
                    onConfirmClick = {
                        //UnlockUC
                        //if(state.success) {navigateToViewChapter}
                        viewChapterModel.unlockAChapter(
                            comicId,
                            state.chapterUnlockNumber,
                            state.priceToUnlockChapter.toLong()
                        )
                    },
                    onDismiss = {
                        showDialog = false
                    },
                    onBuyCoinsClick = {
                        //navigateToBuyCoins
                        viewChapterModel.navigateToCoinShop()
                    }
                )
            }

            else -> {
                // Show comic images
            }
        }



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

                //cut size of page when change mode
                val page = if (page >= images.size && currentPageScroll != 0) {
                    // page will be equal to the current page scroll
                    currentPageScroll - 1
                    Log.e("ViewChapterContent", "Page: $page")
                } else {
                    page
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




