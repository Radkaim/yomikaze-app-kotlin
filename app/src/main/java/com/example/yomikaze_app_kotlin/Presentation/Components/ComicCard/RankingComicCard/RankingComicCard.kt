package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.ComicStatus
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.IconAndNumbers
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.RankingNumberCircle
import com.example.yomikaze_app_kotlin.R

@Composable
fun RankingComicCard(
    rankingNumber: Int,
    image: String,
    comicName: String,
    status: String,
    authorName: String,
    publishedDate: String,
    ratingScore: Float,
    follows: Int,
    views: Int,
    comments: Int,
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface
) {
    // TODO: Implement RankingComicCard
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(119.dp),

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
                modifier = Modifier.fillMaxWidth().offset(x = (2).dp)
            ) {
                RankingNumberCircle(rankingNumber)

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth().offset(x = (-1).dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        //Comic Image
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(image)
                                .build(),
                            contentDescription = "Comic Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(62.dp)
                                .height(80.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = MaterialTheme.shapes.small)
                        )

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(
                                    text = comicName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.offset(y = (-7).dp)
                                ){
                                    ComicStatus(status = status)
                                }

                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 5.dp)
                            ) {
                                Text(
                                    text = "Author: ",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                                Text(
                                    text = authorName,
                                    style = TextStyle(
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 11.sp,
                                    ),
                                )
                            }

                            // Rating score
                            IconAndNumbers(
                                icon = R.drawable.ic_star_fill,
                                iconColor = MaterialTheme.colorScheme.surface,
                                numberRating = ratingScore,
                                numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                numberWeight = FontWeight.Medium,
                                iconWidth = 14,
                                iconHeight = 14,
                                numberSize = 12
                            )

                            // Published Date , Follows, Views, Comments
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(30.dp),
                                modifier = Modifier.padding(top = 10.dp)
                            ) {
                                Text(
                                    text = "Published Date: $publishedDate",
                                    style = TextStyle(
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 10.sp,
                                    )
                                )
                                //Follows, Views, Comments
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.offset(y = (-6).dp)
                                )
                                {
                                    //Follows
                                    IconAndNumbers(
                                        icon = R.drawable.ic_following,
                                        iconColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                                        number = follows,
                                        numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        numberWeight = FontWeight.Light,
                                        iconWidth = 9,
                                        iconHeight = 12,
                                        numberSize = 10
                                    )

                                    //View
                                    IconAndNumbers(
                                        icon = R.drawable.ic_eye,
                                        iconColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                                        number = views,
                                        numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        numberWeight = FontWeight.Light,
                                        iconWidth = 15,
                                        iconHeight = 10,
                                        numberSize = 10
                                    )

                                    //Comments
                                    IconAndNumbers(
                                        icon = R.drawable.ic_comment,
                                        iconColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                                        number = comments,
                                        numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        numberWeight = FontWeight.Light,
                                        iconWidth = 10,
                                        iconHeight = 12,
                                        numberSize = 10
                                    )


                                }
                            }
                        }


                    }

                }

            }
        }
    }
}