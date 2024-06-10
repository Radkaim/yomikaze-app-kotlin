package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat.Style
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material3.MaterialTheme
import com.example.yomikaze_app_kotlin.Presentation.Components.AutoSlider.Autoslider
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicColumn
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicRow
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.RankingComicCard
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.changeColor
import com.example.yomikaze_app_kotlin.R


@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by homeViewModel.state.collectAsState()
    homeViewModel.setNavController(navController)
    HomeContent(state, homeViewModel, navController)
}

@Composable
fun HomeContent(
    state: HomeState,
    viewModel: HomeViewModel,
    navController: NavHostController
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        item {
            if (state.isLoading) {
                Text(
                    text = "Loading...",
                    modifier = Modifier.padding(9.dp)
                )
            } else if (state.images.isNotEmpty()) {
                Autoslider(images = state.images)
            } else {
                // Show a placeholder or message when there are no images
                Text("No images available")
            }
        }

        if (state.isUserLoggedIn) {
//            item {
//                Button(
//                    onClick = { viewModel.onViewMoreHistoryClicked() },
//                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
//                ) {
//                    Text(text = "Click me")
//                }
//            }

            item {
                Row(modifier = Modifier.padding(start = 8.dp, end = 8.dp),) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = "",
//                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "History",
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.clickable { }) {
                        Text(
                            text = "More",
                            modifier = Modifier.padding(top = 8.dp, end = 5.dp),
                            fontSize = 12.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(top = 9.dp, end = 8.dp)
                                .width(12.dp)
                                .height(12.dp)

                            )

                    }
                }
                CardComicRow(navController)

            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(start = 8.dp, end = 15.dp)) {
                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_ranking_home),
                        contentDescription = ""
                    )
                    Text(
                        text = "Ranking",
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.clickable { viewModel.onViewMoreHistoryClicked() }) {
                        Text(
                            text = "More",
                            modifier = Modifier.padding(top = 8.dp, end = 5.dp),
                            fontSize = 12.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(top = 9.dp, end = 8.dp)
                                .width(12.dp)
                                .height(12.dp)
                        )
                    }

                }
            }

            item {
                RankingComicCard(
                    rankingNumber = 1,
                    image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
                    comicName = "Hunter X Hunter",
                    status = "On Going",
                    authorName = "Yoshihiro Togashi",
                    publishedDate = "1998-03-03",
                    ratingScore = 9.5f,
                    follows = 100,
                    views = 100,
                    comments = 10
                )
            }
            item {
                RankingComicCard(
                    rankingNumber = 2,
                    image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
                    comicName = "Hunter X Hunter",
                    status = "On Going",
                    authorName = "Yoshihiro Togashi",
                    publishedDate = "1998-03-03",
                    ratingScore = 9.5f,
                    follows = 100,
                    views = 100,
                    comments = 10
                )
            }
            item {
                RankingComicCard(
                    rankingNumber = 3,
                    image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
                    comicName = "Hunter X Hunter",
                    status = "On Going",
                    authorName = "Yoshihiro Togashi",
                    publishedDate = "1998-03-03",
                    ratingScore = 9.5f,
                    follows = 100,
                    views = 100,
                    comments = 10
                )
            }

            item {
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
                CardComicRow(navController)

            }

        }
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
    HomeView(navController = navController)
}
