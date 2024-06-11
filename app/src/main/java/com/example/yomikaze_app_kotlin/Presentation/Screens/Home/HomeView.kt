package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Domain.Model.Comic
import com.example.yomikaze_app_kotlin.Presentation.Components.AutoSlider.Autoslider
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicRow
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.ItemRankingTabHome
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.RankingComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Networks.NetworkDisconnectedDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.ComponentRectangle
import com.example.yomikaze_app_kotlin.R


@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by homeViewModel.state.collectAsState()

    homeViewModel.setNavController(navController)
    HomeContent(state, homeViewModel, navController)

    if (state.isNetworkAvailable.not()) {
        // Show dialog when network is disconnected
        NetworkDisconnectedDialog()
    }
}

@Composable
fun HomeContent(
    state: HomeState,
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    val comics = getListComicForRanking() // test data

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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

fun getListComicForRanking(): List<Comic> {
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

@Composable
fun showAutoSlider(state: HomeState, images: List<String>) {
    if (state.isLoading) {
        ComponentRectangle()
    } else if (state.images.isNotEmpty() && !state.isLoading) {
        Autoslider(images = state.images)
    }
}

@Composable
fun showHistory(navController: NavHostController, viewModel: HomeViewModel) {
    Row(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        Spacer(modifier = Modifier.width(10.dp))
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
    CardComicRow(navController)
}

@Composable
fun showRanking(viewModel: HomeViewModel) {
    Spacer(modifier = Modifier.height(10.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
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


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ItemRankingTabHome(
                tabName = "Hot",
                isSelected = true,
                modifier = Modifier.clickable { /*TODO: Implement Hot tab */ })
            ItemRankingTabHome(
                tabName = "Rating",
                modifier = Modifier.clickable {/*TODO: Implement Rating tab */ })
            ItemRankingTabHome(
                tabName = "Comment",
                modifier = Modifier.clickable {/*TODO: Implement Comment tab */ })
            ItemRankingTabHome(
                tabName = "Follow",
                modifier = Modifier.clickable { /*TODO: Implement Follow tab */ })
        }

        showRankingComicCard()

    }


}


@Composable
fun showRankingComicCard() {
    val comics = getListComicForRanking()
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp), // 15.dp space between each card
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
                modifier = Modifier.fillMaxWidth() // Make sure each card takes the full width
            )
            // Add space between each card
        }
    }
}

@Composable
fun showWeekly(state: HomeState, navController: NavHostController) {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .padding(start = 8.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_weekly_home),
            contentDescription = ""
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
    CardComicRow(navController)
    //CardComicRow(navController)
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
    HomeView(navController = navController)
}
