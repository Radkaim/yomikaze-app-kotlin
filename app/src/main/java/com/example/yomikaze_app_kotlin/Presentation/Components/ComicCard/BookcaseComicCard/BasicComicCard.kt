package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.cutAuthorName
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.processNameByComma
import com.example.yomikaze_app_kotlin.R

@Composable
fun BasicComicCard(
    comicName: String,
    image: String,
    comicChapter: String? = null,
    isLibrarySearchComicCard: Boolean = false,
    authorName: List<String>? = null,
    // averageRatingNumber: Float,
    onClick: () -> Unit
) {

    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(170.dp) // Adjusted height for better visual balance
            .width(90.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.small
            )
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small),
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .height(150.dp) // Adjusted height for better visual balance
                .width(80.dp)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onSurface // Set the background color of the card to white
            )
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                        top = 4.dp,
                        bottom = 5.dp
                    )
            ) {
                //Comic Image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder),
                    contentDescription = "Comic Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(80.dp)
                        .height(100.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.small
                        )
                        .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
                )

                Text(
                    text = comicName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 3.dp, top = 4.dp)
                )

                if (isLibrarySearchComicCard) {
                    Text(
                        text = cutAuthorName(processNameByComma(authorName!!)),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 3.dp, top = 10.dp)
                    )
                } else {
                    //history
                    Text(
                        text ="Last: Ch.$comicChapter",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 3.dp, top = 10.dp)
                    )
                }
            }

//            Box(
//                modifier = Modifier
//                    .padding(start = 10.dp)
//                    .offset(y = (-5).dp)
//            ) {
//                IconAndNumbers(
//                    icon = R.drawable.ic_star_fill,
//                    iconColor = MaterialTheme.colorScheme.surface,
//                    numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
//                    numberWeight = FontWeight.Medium,
//                    numberRating = averageRatingNumber,
//                    iconWidth = 13,
//                    iconHeight = 14,
//                    numberSize = 12,
//                )
//            }

        }
    }
}
