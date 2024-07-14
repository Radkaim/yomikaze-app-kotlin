package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage.FlipPager
import com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage.FlipPagerOrientation
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.ChapterBottomNavBar
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NoNetworkAvailable
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.LibraryViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailState
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailViewModel
import com.example.yomikaze_app_kotlin.R

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
    if (CheckNetwork()) {
        Log.e("ViewChapter", "Network Available")
        LaunchedEffect(Unit) {
            viewChapterModel.getPagesByChapterNumberOfComic(comicId, chapterNumber)
        }
        ViewChapterContent(
            state = state,
            navController = navController,
            viewChapterModel = viewChapterModel
        )
    } else {
        Log.e("ViewChapter", "No Network Available")
        LaunchedEffect(Unit) {
            viewChapterModel.getPageByComicIdAndChapterNumberInDB(comicId, chapterNumber)
        }
        if (state.isPagesExistInDB) {
            ViewChapterContent(
                state = state,
                navController = navController,
                viewChapterModel = viewChapterModel
            )
        } else {
            Scaffold(
                topBar = {
                    CustomAppBar(
                        title = "View Chapter",
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
                    ChapterBottomNavBar(
                        navController = navController,
                        canPrevious = false,
                        canNext = true
                    )
                }
            )
            {
                NoNetworkAvailable()
            }
        }
    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewChapterContent(
    state: ViewChapterState,
    navController: NavController,
    viewChapterModel: ViewChapterModel
) {

    var isScrolling by remember {
        mutableStateOf(true)
    }

    val images = state.pages


    val pagerState = rememberPagerState { images.size }

    val scrollState = rememberLazyListState()

    val currentPage = pagerState.currentPage + 1
    val currentPageScroll = scrollState.firstVisibleItemIndex + 1
    val imagePath = if (CheckNetwork()) APIConfig.imageAPIURL.toString() else {
        ""
    }
    Scaffold(
        topBar = {
            CustomAppBar(

                title = if (isScrolling)
                    "Scroll Chapter ($currentPageScroll / ${images.size})"
                else "View Chapter ($currentPage / ${images.size})",

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
            ChapterBottomNavBar(
                navController = navController,
                canPrevious = false,
                canNext = true
            )
        }
    )
    {
        if (isScrolling) {
            // Scroll View
            // Comic Image
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = scrollState

            ) {
                // Icon change scroll
                item {
                    // Icon change scroll
                    IconButton(onClick = { isScrolling = !isScrolling }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Change Scroll"
                        )
                    }
                }

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
                                .fillMaxHeight()
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
            // Icon change scroll
            IconButton(onClick = { isScrolling = !isScrolling }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Change Scroll"
                )
            }
            ViewChapterFlipPager(
                images = images,
                pagerState = pagerState,
                navController = navController
            )
        }
    }
}

@Composable
fun ViewChapterFlipPager(
    images: List<String>,
    pagerState: androidx.compose.foundation.pager.PagerState,
    navController: NavController
) {

    var orientation: FlipPagerOrientation by remember {
        mutableStateOf(FlipPagerOrientation.Vertical)
    }

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

    //button to change orientation
    IconButton(
        onClick = {
            orientation = if (orientation == FlipPagerOrientation.Vertical) {
                FlipPagerOrientation.Horizontal
            } else {
                FlipPagerOrientation.Vertical
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Change Orientation"
        )
    }
}


/**
 * TODO : dialog for menu option
 */

@Composable
fun AddToLibraryDialog(
    comicId: Long,
    comicDetailViewModel: ComicDetailViewModel,
    state: ComicDetailState,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        comicDetailViewModel.getAllCategory()
    }


    //val categories = state.categoryList.map { it.name }
    val categories = state.categoryList
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedCategories by remember { mutableStateOf(listOf<Long>()) }

    val libraryViewModel = hiltViewModel<LibraryViewModel>()
    val libraryState by libraryViewModel.state.collectAsState()

    LaunchedEffect(key1 = libraryState.isCreateCategorySuccess) {
        Log.d("AddToLibraryDialog", "Create category success")
        comicDetailViewModel.getAllCategory()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.7f))
                .clickable { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {}
        }
    }
}
