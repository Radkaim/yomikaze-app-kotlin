package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.R

@Composable
fun ComicCateCard(
    comicId: Long,
    image: String,
    comicName: String,
    status: String,
    authorNames: List<String>,
    publishedDate: String,
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface,
    isDeleted: Boolean = false,
    onDeleteClicked: () -> Unit? = {},
    modifier: Modifier,
    onClicked: () -> Unit? = {}
) {

    Surface(
        modifier = modifier.then(
            if (isDeleted) {
                Modifier
                    .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
                    .clickable {
                        onClicked()
                    }

            } else {
                Modifier
            }),
        color = backgroundColor,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (-1).dp)
                .clickable {
                    onClicked()
                }
        ) {
            if (isDeleted) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(
                            top = 9.dp,
                            end = 8.dp
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Delete Icon",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .width(15.dp)
                            .height(17.dp)
                            .clickable {
                                //TODO: Implement delete comic
                                onDeleteClicked()
                            }
                    )
                }

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(start = (25).dp).fillMaxWidth()
            ) {

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
                    verticalArrangement = Arrangement.SpaceBetween,
                    //modifier = Modifier.padding(top = 5.dp)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.offset(y = (3).dp).padding(bottom = 25.dp)
                    ) {
                        Text(
                            text = cutComicName(comicName),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Box(
                            // contentAlignment = Alignment.Center,
                            modifier = Modifier.offset(y = (-8).dp)
                        ) {
                            TagComponent(status = status)
                        }

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.offset(y = (-5).dp).padding(bottom = 10.dp)
                    ) {
                        Text(
                            text = "Author: ",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = cutAuthorName(processNameByComma(authorNames)),
                            style = TextStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Light,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                        )
                    }


                    // Published Date , Follows, Views, Comments
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Text(
                            text = "Published Date: " + changeDateTimeFormat(publishedDate),
                            style = TextStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                }
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

