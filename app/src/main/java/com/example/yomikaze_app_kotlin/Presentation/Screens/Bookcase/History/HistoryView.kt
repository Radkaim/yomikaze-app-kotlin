package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.History

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard.BookcaseComicCard

@Composable
fun HistoryView(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    navController: NavController
) {
    historyViewModel.setNavController(navController)

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
            comicId = 3,
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
            comicId = 4,
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
            comicId = 5,
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
        ),  Comic(
            comicId = 6,
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
            comicId = 7,
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
        ),
    )

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
        // consume the NormalComicCard composable
        showListHistories(comicsListCardModel, historyViewModel)

    }

}
@Composable
fun showListHistories(
    comicsListCardModel: List<Comic>,
    historyViewModel: HistoryViewModel,
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp) // 8.dp space between each item
    ) {
        items(comicsListCardModel) { comic ->
            BookcaseComicCard(
                comicId = comic.comicId,
                image = comic.image,
                comicName = comic.comicName,
                status = comic.status,
                authorName = comic.authorName,
                isHistory = true,
                lastChapter = comic.comicId.toString(),
                publishedDate = comic.publishedDate,
                backgroundColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(119.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable {
                        //navController.navigate("comicDetail/${comic.comicId}")
                        historyViewModel.navigateToViewChapter(comic.comicId)
                        // TODO change to viewModel.navigateToComicDetail(comic.comicId) if using viewModel
                    }
            )
        }
    }
}