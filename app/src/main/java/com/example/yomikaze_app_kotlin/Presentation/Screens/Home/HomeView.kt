package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

//import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicRow
import CardComicHistoryHome
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Core.Networks.ConnectionState
import com.example.yomikaze_app_kotlin.Core.Networks.connectivityState
import com.example.yomikaze_app_kotlin.Domain.Models.Comic
import com.example.yomikaze_app_kotlin.Presentation.Components.AutoSlider.Autoslider
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicItem
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicWeeklyHome
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.ItemRankingTabHome
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.RankingComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialogs.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.ComponentRectangle
import com.example.yomikaze_app_kotlin.R


@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,

    ) {
    val state by homeViewModel.state.collectAsState()

    homeViewModel.setNavController(navController)

    // This will cause re-composition on every network state change
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available


    if (isConnected) {
        // Show UI when connectivity is available
        HomeContent(state, homeViewModel, navController)
    } else {
        // Show UI for No Internet Connectivity
        NetworkDisconnectedDialog()
        NoDataAvailable()
    }
}

@Composable
fun NoDataAvailable() {
    Text(
        text = "No data available",
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

@Composable
fun HomeContent(
    state: HomeState,
    viewModel: HomeViewModel,
    navController: NavController
) {
    val comics = getListComicForRanking() // test data
    val comic = getListCardComicHistory()
    val comicCard = getListCardComicWeekly()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(bottom = 55.dp)
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    )
    {
        item {
            showAutoSlider(state = state, images = state.images)
        }

        if (state.isUserLoggedIn) {
            item {
                showHistory(navController, viewModel)
            }
        }

        item {
            showRanking(viewModel = viewModel)
        }

        item {
            showWeekly(state = state, navController = navController)
        }
    }
}

fun getListComicForRanking(): List< Comic > {
    val comics = listOf(
        Comic(
            comicId = 1,
            rankingNumber = 1,
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicName = "Hunter X Hunter",
            status = "On Going",
            authorName = "Yoshihiro Togashi",
            publishedDate = "1998-03-03",
            ratingScore = 9.5f,
            follows = 100,
            views = 100,
            comments = 100
        ),
        Comic(
            comicId = 2,
            rankingNumber = 2,
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicName = "Hunter X Hunter12323",
            status = "On Going",
            authorName = "Yoshihiro Togashi",
            publishedDate = "1998-03-03",
            ratingScore = 9.5f,
            follows = 100,
            views = 100,
            comments = 10
        ),
        Comic(
            comicId = 3,
            rankingNumber = 3,
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f",
            comicName = "Hunter X Hunter12323",
            status = "On Going",
            authorName = "Yoshihiro Togashi",
            publishedDate = "1998-03-03",
            ratingScore = 9.5f,
            follows = 100,
            views = 100,
            comments = 10
        )
    )
    return comics
}

//@Composable


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
    if (state.isLoading) {
        ComponentRectangle()
    } else if (state.images.isNotEmpty() && !state.isLoading) {
        Autoslider(images = state.images)
    }
}

@Composable
fun showHistory(navController: NavController, viewModel: HomeViewModel) {
    Column(
//        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.clickable { viewModel.onViewMoreHistoryClicked() }) {
                Text(
                    text = "More",
                    modifier = Modifier.padding(top = 8.dp, end = 5.dp),
                    fontSize = 10.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 10.dp, end = 8.dp)
                        .width(8.dp)
                        .height(8.dp)
                        .clickable { viewModel.onViewMoreHistoryClicked() }

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
//            .background(color = MaterialTheme.colorScheme.tertiary),
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
fun showRanking(viewModel: HomeViewModel) {
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
                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row() {
                Text(
                    text = "More",
                    modifier = Modifier.padding(top = 8.dp, end = 5.dp),
                    fontSize = 10.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 10.dp, end = 8.dp)
                        .width(8.dp)
                        .height(8.dp)
                        .clickable { viewModel.onViewRankingMore() }
                )
            }

        }

        //lazyRow
        val listTabs = listOf("Hot", "Rating", "Comment", "Follow") //-> for future implementation
        // create a comics list mutable state

        showTabRow()
        showRankingComicCard(viewModel = viewModel)

    }
}

@Composable
fun showTabRow() {
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
                onClick = { setSelectedTabIndex(index)
                Log.d("HomeView", "Selected tab index: $index") },
                modifier = Modifier
            )
        }
    }
}


@Composable
fun showRankingComicCard(viewModel: HomeViewModel) {
    val comics = getListComicForRanking()
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
        comics.forEach { comic ->
            RankingComicCard(
                comicId = comic.comicId,
                rankingNumber = comic.rankingNumber,
                image = comic.image,
                comicName = comic.comicName,
                status = comic.status,
                authorName = comic.authorName,
                publishedDate = comic.publishedDate,
                ratingScore = comic.ratingScore,
                follows = comic.follows,
                views = comic.views,
                comments = comic.comments,
                modifier = Modifier
                    .fillMaxWidth() // Make sure each card takes the full width
                    .clickable { viewModel.onComicRankingClicked(comic.comicId) }
            )
            // Add space between each card
        }
    }
}

@Composable
fun showWeekly(state: HomeState, navController: NavController) {
//    Spacer(modifier = Modifier.height(10.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        if (state.isLoading) {
            Text(
                text = "Loading...",
                modifier = Modifier.padding(9.dp)
            )
        } else if (state.images.isNotEmpty()) {
            Autoslider(images = state.images)
        } else {
            Text("No images available")
        }
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

@Preview
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
//    HomeView(navController = navController)
}
