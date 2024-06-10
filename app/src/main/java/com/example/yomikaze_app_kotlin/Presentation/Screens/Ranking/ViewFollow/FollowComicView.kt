package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewFollow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.NormalComicCard

@Composable
fun FollowComicView() {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp), // 8.dp space between each card
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
        NormalComicCard(
            comicId = 1,
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicName = "Hunter X Hunter",
            status = "On Going",
            authorName = "Yoshihiro Togashi",
            publishedDate = "1998-03-03",
            ratingScore = 9.5f,
            follows = 100,
            views = 100,
            comments = 100,
            isDeleted = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(119.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.small
                ),

            )
        NormalComicCard(
            comicId = 2,
            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            comicName = "Hunter X Hunter67677667",
            status = "On Going",
            authorName = "Yoshihiro Togashi",
            publishedDate = "1998-03-03",
            ratingScore = 9.5f,
            follows = 100,
            views = 100,
            comments = 100,
            modifier = Modifier
                .fillMaxWidth()
                .height(126.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.small
                ),
        )
    }
}