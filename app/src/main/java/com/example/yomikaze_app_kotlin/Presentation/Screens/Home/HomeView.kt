package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

//import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicRow
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Presentation.Components.AutoSlider.Autoslider
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard.BasicComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.ItemRankingTabHome
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.NormalComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.RankingComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Navigation.BottomNav.HomeBottomNavBar
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.ComponentRectangle
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.NormalComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.TopAppBar.DefaultTopAppBar
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.SearchTopAppBar
import com.example.yomikaze_app_kotlin.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by homeViewModel.state.collectAsState()
    val searchWidgetState by homeViewModel.searchWidgetState
    val searchTextState by homeViewModel.searchTextState

    homeViewModel.setNavController(navController)


//    // This will cause re-composition on every network state change
//    val connection by connectivityState()
//    val isConnected = connection === ConnectionState.Available

    Scaffold(
        topBar = {
            MainHomeAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = { homeViewModel.updateSearchText(newValue = it) },
                onCloseClicked = {
                    homeViewModel.updateSearchText(newValue = "")
                    homeViewModel.updateSearchWidgetState(newState = SearchWidgetState.CLOSE)
                    homeViewModel.updateSearchResult(newSearchResult = emptyList()) // Cập nhật searchResult
                    homeViewModel.updateTotalResults(0)
                },
                onSearchClicked = {
                    //  Log.d("HomeView", "Search text: $searchTextState")
                    homeViewModel.searchComic(searchTextState)

                },
                onSearchTriggered = { homeViewModel.updateSearchWidgetState(SearchWidgetState.OPEN) }
            )
        },
        bottomBar = {
            HomeBottomNavBar(
                navController = navController
            )
        }
    )
    {
        if (CheckNetwork()) {
            // Show UI when connectivity is available
            HomeContent(
                state,
                homeViewModel,
                navController,
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onCloseClicked = {
                    homeViewModel.updateSearchWidgetState(newState = SearchWidgetState.CLOSE)
                }

            )
        } else {
            // Show UI for No Internet Connectivity
            UnNetworkScreen()
        }
    }
}


@Composable
fun MainHomeAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSE -> {
            // Show search widget
            DefaultTopAppBar(
                navigationIcon = {},
                actions = {},
                isProfile = false,
                onLogoClicked = {},
                onSearchClicked = { onSearchTriggered() },
                onSettingClicked = {}
            )
        }

        SearchWidgetState.OPEN -> {
            // Show normal app bar
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp)
            ) {
                SearchTopAppBar(
                    searchText = searchTextState,
                    onTextChange = onTextChange,
                    onCLoseClicked = { onCloseClicked() },
                    onSearchClicked = { onSearchClicked() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeContent(
    state: HomeState,
    homeViewModel: HomeViewModel,
    navController: NavController,
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onCloseClicked: () -> Unit = {},

    ) {
    if (searchWidgetState == SearchWidgetState.OPEN) {

        Scaffold(
            topBar = {},

            bottomBar = {}
        )
        {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .wrapContentSize(Alignment.Center)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(
                        top = 10.dp,
                        start = 4.dp,
                        end = 4.dp,
                        bottom = 4.dp
                    ),// Optional padding for the entire list,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    if (state.totalResults != 0) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Results: " + state.totalResults.toString(),
                                color = MaterialTheme.colorScheme.inverseSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(bottom = 10.dp)
                            )
                        }

                    }
                }

                items(state.searchResult.value) { comic ->
                    //  Spacer(modifier = Modifier.height(10.dp))
                    SearchResultItem(comic = comic, homeViewModel = homeViewModel)
                }

                item {
                    if (state.isSearchLoading) {
                        repeat(2) {
                            NormalComicCardShimmerLoading()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                item {
                    if (state.searchResult.value.isNotEmpty()) {
                        Text(
                            text = "Show More",
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .clickable { homeViewModel.onAdvanceSearchClicked(searchTextState) },
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    } else {
        Scaffold(
            topBar = {},

            bottomBar = {
                HomeBottomNavBar(
                    navController = navController
                )
            }
        )
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 55.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            )
            {
                item {
                    showComicCarouselByViewRanking(state = state, homeViewModel = homeViewModel)
                }
                Log.d("HomeView", "State images: ${homeViewModel.checkUserIsLogin()}")
                if (homeViewModel.checkUserIsLogin()) {
                    item {
                        showHistory(navController, homeViewModel, state)
                    }
                }

                item {
                    showRanking(homeViewModel = homeViewModel, state = state)
                }

                item {
                    showWeekly(
                        homeViewModel = homeViewModel,
                        state = state,
                        navController = navController
                    )
                }

            }
        }

    }
}

@Composable
private fun SearchResultItem(
    homeViewModel: HomeViewModel,
    comic: ComicResponse
) {
    NormalComicCard(
        comicId = comic.comicId,
        image = APIConfig.imageAPIURL.toString() + comic.cover,
        comicName = comic.name,
        status = comic.status,
        authorNames = comic.authors,
        publishedDate = comic.publicationDate,
        ratingScore = comic.averageRating,
        follows = comic.follows,
        views = comic.views,
        comments = comic.comments,
        isSearch = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(119.dp)
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.small
            ),
        onClicked = { homeViewModel.onComicSearchClicked(comic.comicId) }
    )
}


@Composable
fun showComicCarouselByViewRanking(
    homeViewModel: HomeViewModel,
    state: HomeState
) {
    val comics = state.listComicCarousel
    val images = mutableMapOf<Long, String>()

    for (comic in comics) {
        val imageUrl = if (comic.banner == null) {
            APIConfig.imageAPIURL.toString() + comic.cover
        } else {
            APIConfig.imageAPIURL.toString() + comic.banner
        }
        images[comic.comicId] = imageUrl
    }


    Box(modifier = Modifier.padding(top = 60.dp)) {
        if (state.isCoverCarouselLoading) {
            ComponentRectangle()
        } else if (images.isNotEmpty() && !state.isCoverCarouselLoading) {
            Autoslider(imagesWithIds = images, onClick = { comicId ->
                homeViewModel.onNavigateComicDetail(comicId)
            })
        }
    }
}

@Composable
fun showHistory(navController: NavController, viewModel: HomeViewModel, state: HomeState) {

    LaunchedEffect(Unit) {
        viewModel.getHistories()
    }
    if (state.listHistoryRecords.isNotEmpty()) {
        // show 3 shimmer loading cards
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 1.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = "Icon History",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "History",
                    modifier = Modifier.padding(top = 8.dp, start = 5.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.clickable { viewModel.onViewMoreHistoryClicked() }) {
                    Text(
                        text = "More",
                        modifier = Modifier.padding(top = 15.dp, end = 10.dp),
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 18.dp, end = 15.dp)
                            .width(8.dp)
                            .height(8.dp)
                            .clickable { viewModel.onViewMoreHistoryClicked() },
                        tint = MaterialTheme.colorScheme.onSecondaryContainer

                    )
                }
            }

        showHistoryCardComic(state = state, homeViewModel = viewModel)
    }
}

@Composable
fun showHistoryCardComic(
    state: HomeState,
    homeViewModel: HomeViewModel
) {
    val comics = state.listHistoryRecords
//    Log.d("HomeView", "History records: $comics")
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 5.dp, bottom = 8.dp, top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        comics.forEach { historyRecord ->
            BasicComicCard(
                comicId = historyRecord.comic.comicId,
                image = APIConfig.imageAPIURL.toString() + historyRecord.comic.cover,
                comicName = historyRecord.comic.name,
                comicChapter = historyRecord.chapter.number.toString(),
                onClick = {
                    homeViewModel.onHistoryComicClicked(
                        historyRecord.comic.comicId,
                        historyRecord.chapter.number,
                        historyRecord.pageNumber
                    )
                },
                // averageRatingNumber = comic.averageRatingNumber
            )
        }

    }
}


@Composable
fun showRanking(homeViewModel: HomeViewModel, state: HomeState) {

    // Duy trì trạng thái của tab được chọn
    val (selectedTabIndex, setSelectedTabIndex) = remember { mutableStateOf(0) }

    Spacer(modifier = Modifier.height(10.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.padding(
                start = 8.dp, end = 15.dp
            )
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_ranking_home),
                contentDescription = "Icon Ranking",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Ranking",
                modifier = Modifier.padding(top = 8.dp, start = 5.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.weight(1f))
            Row() {
                Text(
                    text = "More",
                    modifier = Modifier.padding(top = 15.dp, end = 10.dp),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 18.dp, end = 1.dp)
                        .width(8.dp)
                        .height(8.dp)
                        .clickable { homeViewModel.onViewRankingMore(selectedTabIndex) },
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

        }


        // create a comics list mutable state

        showTabRow(
            homeViewModel = homeViewModel,
            state = state,
            selectedTabIndex,
            setSelectedTabIndex
        )
        showRankingComicCard(homeViewModel = homeViewModel, state = state)

    }
}


@Composable
fun showTabRow(
    homeViewModel: HomeViewModel,
    state: HomeState,
    selectedTabIndex: Int,
    setSelectedTabIndex: (Int) -> Unit
) {


    // Các tên tab và các hành động tương ứng
    val tabs = listOf("Hot", "Rating", "Comment", "Follow")

    LaunchedEffect(Unit) {
        homeViewModel.getComicByViewRanking(1, 5)
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        tabs.forEachIndexed { index, tabName ->
            ItemRankingTabHome(
                tabName = tabName,
                isSelected = index == selectedTabIndex,
                onClick = {
                    setSelectedTabIndex(index)
                    Log.d("HomeView", "Selected tab index: $index")
                    tabSelected(index, homeViewModel, state)
                },
                modifier = Modifier
            )
        }
    }
}


fun tabSelected(tabIndex: Int, homeViewModel: HomeViewModel, state: HomeState) {
    val size = 3
    val page = 1
    when (tabIndex) {
        0 -> {
            // Hot
            homeViewModel.getComicByViewRanking(page, size)
        }

        1 -> {
            // Rating
            homeViewModel.getComicByRatingRanking(page, size)
        }

        2 -> {
            // Comment
            homeViewModel.getComicByCommentRanking(page, size)
        }

        3 -> {
            // Follow
            homeViewModel.getComicByFollowRanking(page, size)
        }
    }
}


@Composable
fun showRankingComicCard(homeViewModel: HomeViewModel, state: HomeState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp), // 15.dp space between each card
        modifier = Modifier
            .padding(
                top = 8.dp,
                start = 4.dp,
                end = 4.dp,
                bottom = 4.dp
            ) // Optional padding for the entire list
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize(Alignment.Center)
    ) {
        if (state.isLoadingRanking) {
            // show 3 shimmer loading cards
            val list = listOf(1, 2, 3)
            repeat(list.size) {
                NormalComicCardShimmerLoading()
            }
        }
        state.listRankingComics.forEachIndexed { index, comic ->
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
                modifier = Modifier.fillMaxWidth(), // Make sure each card takes the full width
                onClicked = { homeViewModel.onNavigateComicDetail(comic.comicId) }
            )
            // Add space between each card
        }

    }
}

@Composable
fun showWeekly(
    homeViewModel: HomeViewModel,
    state: HomeState,
    navController: NavController
) {
    LaunchedEffect(Unit){
        homeViewModel.getComicWeekly()
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = -(10).dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 20.dp ,start = 20.dp, bottom = 1.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),

        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_weekly_home),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Weekly",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(top = 5.dp)

            )
            // Spacer(modifier = Modifier.weight(1f))
        }
        showComicCarouselByWeeklyRanking(state = state, homeViewModel = homeViewModel)
    }
}

@Composable
fun showComicCarouselByWeeklyRanking(
    homeViewModel: HomeViewModel,
    state: HomeState
) {
    val comics = state.listComicWeekly
    val images = mutableMapOf<Long, String>()

    for (comic in comics) {
        val imageUrl = if (comic.banner == null) {
            APIConfig.imageAPIURL.toString() + comic.cover
        } else {
            APIConfig.imageAPIURL.toString() + comic.banner
        }
        images[comic.comicId] = imageUrl
    }

    Box(modifier = Modifier.padding(top = 10.dp)) {
        if (state.isLoadingComicWeekly) {
            ComponentRectangle()
        } else if (images.isNotEmpty() && !state.isLoadingComicWeekly) {
            Autoslider(imagesWithIds = images, onClick = { comicId ->
                homeViewModel.onNavigateComicDetail(comicId)
            })
        }
    }
}

////@Composable
//fun showWeeklyCardComic() {
//
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(start = 10.dp, end = 8.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp), // Reduced horizontal spacing
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//
//        comics.forEach { comic ->
//            CardComicWeeklyHome(
//                image = comic.image,
//                comicName = comic.comicName,
//                comicAuth = comic.comicAuth
//            )
//        }
//    }
//
//}


//@Composable
//fun ShowListWithThreeItemsPerRow(list: List<String>) {
//    LazyRow {
//        items(list.chunked(3)) { chunkedList ->
//            RowWithThreeItems(chunkedList)
//        }
//    }
//}
//
//@Composable
//fun RowWithThreeItems(list: List<String>) {
//    Row {
//        list.forEach { item ->
//
//        }
//    }
//}
