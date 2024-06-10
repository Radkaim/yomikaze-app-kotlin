package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewComment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.RankingComicCard

@Composable
fun CommentComicView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize(Alignment.Center)
    ) {
        RankingComicCard(
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
            comments = 100,
            modifier = Modifier
        )

    }
}