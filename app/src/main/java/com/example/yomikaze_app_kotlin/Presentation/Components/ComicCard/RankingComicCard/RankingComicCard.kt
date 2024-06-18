package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.RankingNumberCircle

@Composable
fun RankingComicCard(
    comicId: Int,
    rankingNumber: Int,
    image: String,
    comicName: String,
    status: String,
    authorName: String,
    publishedDate: String,
    ratingScore: Float,
    follows: Long,
    views: Long,
    comments: Long,
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier,
) {

    // TODO: Implement RankingComicCard
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(126.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.small
            )
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
            .then(modifier),

        color = backgroundColor,

        ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(
                start = 5.dp,
                end = 5.dp,
                top = 9.dp,
                bottom = 9.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = (2).dp)
            ) {
                RankingNumberCircle(rankingNumber)

                NormalComicCard(
                    comicId = comicId,
                    image = image,
                    comicName = comicName,
                    status = status,
                    authorName = authorName,
                    publishedDate = publishedDate,
                    ratingScore = ratingScore,
                    follows = follows,
                    views = views,
                    comments = comments,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(119.dp)
                )
            }
        }
    }
}