package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewHot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.Model.Comic
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.RankingComicCard

@Composable
fun HotComicView(
    hotComicViewModel: HotComicViewModel = hiltViewModel(),
    navController: NavController) {

    //set navController for viewModel
    hotComicViewModel.setNavController(navController)

    //create listOf comics
    val comicsListCardModel = listOf(
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
        ),  Comic(
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
        ),  Comic(
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
        ),  Comic(
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
        ),  Comic(
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
    )
    //   modifier = Modifier.clickable {
    //                navController.navigate("comicDetail/${1}")
    //            }

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
            items(comicsListCardModel) { comic ->
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
                    backgroundColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.clickable {
                        //navController.navigate("comicDetail/${comic.comicId}")
                        hotComicViewModel.navigateToComicDetail(comic.comicId)
                        // TODO change to viewModel.navigateToComicDetail(comic.comicId) if using viewModel
                    }
                )
            }
        }
    }
}
