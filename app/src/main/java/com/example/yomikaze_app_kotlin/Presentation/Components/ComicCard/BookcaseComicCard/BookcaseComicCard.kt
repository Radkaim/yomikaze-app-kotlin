package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.Download.convertToMbsOrGbs
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.changeDateTimeFormat
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.DeleteConfirmDialogComponent
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.History.HistoryViewModel
import com.example.yomikaze_app_kotlin.R

@Composable
fun BookcaseComicCard(
    comicId: Long,
    image: String,
    comicName: String,
    status: String,
    authorNames: List<String>,
    publishedDate: String,
//    ratingScore: Float,
//    follows: Int,
//    views: Int,
//    comments: Int,
    isHistory: Boolean = false,
    historyRecordId: Long? = null,
    historyViewModel: HistoryViewModel? = null,

    value: String? = null,
    onClicked: () -> Unit = {},
    lastChapter: String? = null,
    isDownloaded: Boolean = false,
    downloadViewModel: DownloadViewModel? = null,

    totalMbs: Long? = null,
    isDeleted: Boolean = false,
    onDeleteClicked: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier,
) {
    var showPopupMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf<Int?>(null) }

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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (-4).dp)
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
                                //TODO: Implement delete history record
//                                test for history record
                                if (isHistory) {
                                    showPopupMenu = true
                                    showDialog = 1

                                }
                                if (isDownloaded) {
                                    showPopupMenu = true
                                    showDialog = 2
                                }

                            }
                    )
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
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

                        TagComponent(status = status)
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
                            text = cutAuthorName(processAuthorName(authorNames)),
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
                                text = convertToMbsOrGbs(totalMbs!!),
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
                        text = "Published Date: ${changeDateTimeFormat(publishedDate)}",
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
    if (showDialog != null) {
        when (showDialog) {

            1 -> {
                DeleteConfirmDialogComponent(
                    key = historyRecordId!!,
                    value = value ?: "",
                    title = "Are you sure you want to delete this history record?",
                    onDismiss = { showDialog = 0 },
                    viewModel = historyViewModel!!
                )
            }

            2 -> {
                DeleteConfirmDialogComponent(
                    key = comicId,
                    value = comicName,
                    title = "Are you sure you want to delete downloaded comic?",
                    onDismiss = { showDialog = 0 },
                    viewModel = downloadViewModel!!
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

private fun processAuthorName(authorNames: List<String>): String {
    return authorNames.joinToString(", ")
}

private fun cutAuthorName(authorName: String): String {
    val authorNameLength = authorName.length
    return if (authorNameLength > 18) {
        authorName.substring(0, 18) + "..."
    } else {
        authorName
    }
}
