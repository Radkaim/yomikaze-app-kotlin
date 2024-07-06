package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage.FlipPager
import com.example.yomikaze_app_kotlin.Presentation.Components.FlipPage.FlipPagerOrientation
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
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
    LaunchedEffect(Unit) {
        viewChapterModel.getPagesByChapterNumberOfComic(comicId, chapterNumber)
    }

    var isScrolling by remember {
        mutableStateOf(true)
    }

    val images = state.pages


    val pagerState = rememberPagerState { images.size }

    val scrollState = rememberLazyListState()

    val currentPage = pagerState.currentPage + 1
    val currentPageScroll = scrollState.firstVisibleItemIndex + 1
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
        })
    {


        if (isScrolling) {
            // Scroll View
            // Comic Image
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = scrollState

            ) {
                // Icon change scroll
               item {
                   // Icon change scroll
                   IconButton(onClick = { isScrolling = !isScrolling}) {
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
                                .data(APIConfig.imageAPIURL.toString() + image)
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
        }
        else{
            // Icon change scroll
            IconButton(onClick = { isScrolling = !isScrolling}) {
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
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        orientation = orientation,
    ) { page ->
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),
        )
        //Comic Image
        images[page].let { image ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(APIConfig.imageAPIURL.toString() + image)
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

