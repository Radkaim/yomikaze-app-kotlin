package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewFollow

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
fun FollowComicView(
    followComicViewModel: FollowComicViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by followComicViewModel.state.collectAsState()
    //set navController for viewModel
    followComicViewModel.setNavController(navController)

    if (CheckNetwork()) {
        followComicViewModel.getComicByFollowRanking()
        FollowComicViewContent(followComicViewModel = followComicViewModel, state = state)
    } else {
        UnNetworkScreen()
    }

//
//    /**
//     * Test Normal comic for library
//     */
//    Column(
//        verticalArrangement = Arrangement.spacedBy(15.dp), // 8.dp space between each card
//        modifier = Modifier
//            .padding(
//                top = 15.dp,
//                start = 4.dp,
//                end = 4.dp,
//                bottom = 4.dp
//            ) // Optional padding for the entire list
//            .background(MaterialTheme.colorScheme.background)
//            .wrapContentSize(Alignment.Center)
//    ) {
//
//        // consume the NormalComicCard composable
//        NormalComicCard(
//            comicId = 1,
//            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
//            comicName = "Hunter X Hunter",
//            status = "On Going",
//            authorNames = listOf("as",""),
//            publishedDate = "1998-03-03",
//            ratingScore = 9.5f,
//            follows = 100,
//            views = 100,
//            comments = 100,
//            isDeleted = false,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(119.dp)
//                .border(
//                    width = 1.dp,
//                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
//                    shape = MaterialTheme.shapes.small
//                ),
//
//            )
//        NormalComicCard(
//            comicId = 2,
//            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
//            comicName = "Hunter X Hunter67677667",
//            status = "On Going",
//            authorNames = listOf("as",""),
//            publishedDate = "1998-03-03",
//            ratingScore = 9.5f,
//            follows = 100,
//            views = 100,
//            comments = 100,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(126.dp)
//                .border(
//                    width = 1.dp,
//                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
//                    shape = MaterialTheme.shapes.small
//                ),
//        )
//    }
//



}


@Composable
fun FollowComicViewContent(
    followComicViewModel: FollowComicViewModel,
    state: FollowComicState,
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
            itemsIndexed(state.listComicByFollowRanking) { index, comic ->
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
                    backgroundColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier,
                    onClicked = { followComicViewModel.navigateToComicDetail(comic.comicId) }
                )
            }
        }
    }
}
