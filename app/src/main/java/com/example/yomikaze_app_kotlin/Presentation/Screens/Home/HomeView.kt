package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

//import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicRow
import CardComicHistoryHome
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Presentation.Components.AutoSlider.Autoslider
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicItem
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicWeeklyHome
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.ItemRankingTabHome
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.NormalComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.RankingComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.ComponentRectangle
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
                    state.searchResult = emptyList()
                },
                onSearchClicked = {
                    Log.d("HomeView", "Search text: $searchTextState")
                    homeViewModel.searchComic(searchTextState)

                },
                onSearchTriggered = { homeViewModel.updateSearchWidgetState(SearchWidgetState.OPEN) }
            )
        }
    )
    {
        if (CheckNetwork()) {
            // Show UI when connectivity is available
            HomeContent(state, homeViewModel, navController, searchWidgetState = searchWidgetState)
            homeViewModel.getComicByViewRanking(3)
        } else {
            // Show UI for No Internet Connectivity
            UnNetworkScreen()
        }
    }
}

@Composable
fun SearchResultItem(
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
            SearchTopAppBar(
                searchText = searchTextState,
                onTextChange = onTextChange,
                onCLoseClicked = { onCloseClicked() },
                onSearchClicked = { onSearchClicked() }
            )
        }
    }
}

@Composable
fun HomeContent(
    state: HomeState,
    homeViewModel: HomeViewModel,
    navController: NavController,
    searchWidgetState: SearchWidgetState,

    ) {
    if (searchWidgetState == SearchWidgetState.OPEN) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 70.dp)
                .wrapContentSize(Alignment.Center)
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = 15.dp,
                    start = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                ),// Optional padding for the entire list,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(state.searchResult) { comic ->
                SearchResultItem(comic = comic, homeViewModel = homeViewModel)
            }
        }
    } else {
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
                showAutoSlider(state = state, images = state.images)
            }
            Log.d("HomeView", "State images: ${homeViewModel.checkUserIsLogin()}")
            if (homeViewModel.checkUserIsLogin()) {
                item {
                    showHistory(navController, homeViewModel)
                }
            }

            item {
                showRanking(homeViewModel = homeViewModel, state = state)
            }

            item {
                showWeekly(state = state, navController = navController)
            }

        }
    }
}



fun getListCardComicHistory(): List<CardComicItem> {
    val comics = listOf(
        CardComicItem(
            comicName = "Comic 1",
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicAuth = "auth",
            comicChapter = "Chapter 1"
        ),
        CardComicItem(
            comicName = "Comic 2",
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicAuth = "auth",
            comicChapter = "Chapter 2"
        ),
        CardComicItem(
            comicName = "Comic 3",
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicAuth = "auth",
            comicChapter = "Chapter 3"
        ),
        CardComicItem(
            comicName = "Comic 3",
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicAuth = "auth",
            comicChapter = "Chapter 3"
        ),
        CardComicItem(
            comicName = "Comic 3",
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicAuth = "auth",
            comicChapter = "Chapter 3"
        )
    )

    return comics
}

fun getListCardComicWeekly(): List<CardComicItem> {
    val comicCard = listOf(
        CardComicItem(
            comicName = "Comic 1",
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicAuth = "auth",
            comicChapter = "Chapter 1"
        ),
        CardComicItem(
            comicName = "Comic 2",
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicAuth = "auth",
            comicChapter = "Chapter 2"
        ),
        CardComicItem(
            comicName = "Comic 3",
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicAuth = "auth",
            comicChapter = "Chapter 3"
        )
    )
    return comicCard
}

@Composable
fun showAutoSlider(state: HomeState, images: List<String>) {
    Box(modifier = Modifier.padding(top = 60.dp)) {
        if (state.isLoading) {
            ComponentRectangle()
        } else if (state.images.isNotEmpty() && !state.isLoading) {

            Autoslider(images = images)
        }
    }
}

@Composable
fun showHistory(navController: NavController, viewModel: HomeViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
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
                modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.clickable { viewModel.onViewMoreHistoryClicked() }) {
                Text(
                    text = "More",
                    modifier = Modifier.padding(top = 8.dp, end = 5.dp),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 10.dp, end = 8.dp)
                        .width(8.dp)
                        .height(8.dp)
                        .clickable { viewModel.onViewMoreHistoryClicked() },
                    tint = MaterialTheme.colorScheme.onSecondaryContainer

                )

            }
        }
    }
    showHistoryCardComic()
}

@Composable
fun showHistoryCardComic() {
    val comics = getListCardComicHistory()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 9.dp, end = 5.dp, bottom = 8.dp, top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        comics.forEach { comic ->
            CardComicHistoryHome(
                image = comic.image,
                comicName = comic.comicName,
                comicChapter = comic.comicChapter
            )
        }
    }

}


@Composable
fun showRanking(homeViewModel: HomeViewModel, state: HomeState) {
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
                modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.weight(1f))
            Row() {
                Text(
                    text = "More",
                    modifier = Modifier.padding(top = 8.dp, end = 5.dp),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 10.dp, end = 8.dp)
                        .width(8.dp)
                        .height(8.dp)
                        .clickable { homeViewModel.onViewRankingMore() },
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

        }

        //lazyRow
        val listTabs = listOf("Hot", "Rating", "Comment", "Follow") //-> for future implementation
        // create a comics list mutable state

        showTabRow(homeViewModel = homeViewModel, state = state)
        showRankingComicCard(homeViewModel = homeViewModel, state = state)

    }
}

@Composable
fun showTabRow(
    homeViewModel: HomeViewModel,
    state: HomeState
) {
    // Duy trì trạng thái của tab được chọn
    val (selectedTabIndex, setSelectedTabIndex) = remember { mutableStateOf(0) }

    // Các tên tab và các hành động tương ứng
    val tabs = listOf("Hot", "Rating", "Comment", "Follow")

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
    when (tabIndex) {
        0 -> {
            // Hot
            homeViewModel.getComicByViewRanking(size)
        }

        1 -> {
            // Rating
            homeViewModel.getComicByRatingRanking(size)
        }

        2 -> {
            // Comment
            homeViewModel.getComicByCommentRanking(size)
        }

        3 -> {
            // Follow
            homeViewModel.getComicByFollowRanking(size)
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
                onClicked = { homeViewModel.onComicRankingClicked(comic.comicId) }
            )
            // Add space between each card
        }
    }
}

@Composable
fun showWeekly(state: HomeState, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 1.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_weekly_home),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Weekly",
                modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            // Spacer(modifier = Modifier.weight(1f))
        }
        showAutoSlider(state = state, images = state.images)
    }
    showWeeklyCardComic()
    showWeeklyCardComic()
}

@Composable
fun showWeeklyCardComic() {
    val comics = getListCardComicWeekly()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp), // Reduced horizontal spacing
        verticalAlignment = Alignment.CenterVertically,
    ) {

        comics.forEach { comic ->
            CardComicWeeklyHome(
                image = comic.image,
                comicName = comic.comicName,
                comicAuth = comic.comicAuth
            )
        }
    }

}


@Composable
fun ShowListWithThreeItemsPerRow(list: List<String>) {
    LazyRow {
        items(list.chunked(3)) { chunkedList ->
            RowWithThreeItems(chunkedList)
        }
    }
}

@Composable
fun RowWithThreeItems(list: List<String>) {
    Row {
        list.forEach { item ->

        }
    }
}
