package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewRating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.RankingComicCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen

@Composable
fun RatingComicView(
    navController: NavController,
    ratingComicViewModel: RatingComicViewModel = hiltViewModel()
) {
    //create listOf comics
    val state by ratingComicViewModel.state.collectAsState()
    //set navController for viewModel
    ratingComicViewModel.setNavController(navController)

    if (CheckNetwork()) {
        ratingComicViewModel.getComicByRatingRanking()
        RatingComicViewContent(ratingComicViewModel = ratingComicViewModel, state = state)
    } else {
        UnNetworkScreen()
    }
}

@Composable
fun RatingComicViewContent(
    ratingComicViewModel: RatingComicViewModel,
    state: RatingComicState,
) {
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
            verticalArrangement = Arrangement.spacedBy(8.dp) // 8.dp space between each item
        ) {
            itemsIndexed(state.listComicByRatingRanking) { index, comic ->
                RankingComicCard(
                    comicId = comic.comicId,
                    rankingNumber = index + 1,
                    image = APIConfig.imageAPIURL.toString() + comic.cover,
                    comicName = comic.name,
                    status = comic.status,
                    authorNames = comic.authors,
                    publishedDate = comic.publicationDate,
                    ratingScore = comic.rating,
                    follows = comic.follows,
                    views = comic.views,
                    comments = comic.comments,
                    backgroundColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier,
                    onClicked = { ratingComicViewModel.navigateToComicDetail(comic.comicId) }
                )
            }
        }
    }
}
