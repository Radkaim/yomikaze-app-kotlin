package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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

@Composable
fun BookcaseComicCard(
    comicId: Int,
    image: String,
    comicName: String,
    status: String,
    authorName: String,
    publishedDate: String,
//    ratingScore: Float,
//    follows: Int,
//    views: Int,
//    comments: Int,
    isHistory: Boolean = false,
    lastChapter: String? = null,
    isDownloaded: Boolean = false,
    totalMbs: Float? = null,
    isDeleted: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(126.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.small
            )
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small),
        color = backgroundColor,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp)
        ) {
            // Comic Image
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
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.small
                    )
                    .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = cutComicName(comicName),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )

                    ComicStatus(status = status)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
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

                if (isHistory) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Last Chapter: ",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = lastChapter ?: "",
                            style = TextStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp,
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                if (isDownloaded) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total MBs: ",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = totalMbs.toString() + " MBs",
                            style = TextStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp,
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Text(
                    text = "Published Date: $publishedDate",
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light,
                        fontSize = 10.sp,
                    )
                )
            }
        }
    }
}

private fun cutComicName(comicName: String): String {
    val comicNameLength = comicName.length
    return if (comicNameLength > 18) {
        comicName.substring(0, 18) + "..."
    } else {
        comicName
    }
}