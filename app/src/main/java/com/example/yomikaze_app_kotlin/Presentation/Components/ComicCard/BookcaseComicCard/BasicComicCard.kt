package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.AddToLibraryDialog
import com.example.yomikaze_app_kotlin.R

@Composable
fun BasicComicCard(
    comicName: String,
    comicId: Long,
    image: String,
    comicChapter: String? = null,
    isLibrarySearchComicCard: Boolean = false,
    authorName: List<String>? = null,
    // averageRatingNumber: Float,
    isDelete: Boolean? = false,
    onDeletedClicked: () -> Unit? = {},
    onAddClicked: () -> Unit? = {},

    onClick: () -> Unit
) {
    var height = 170.dp
    if (isDelete == true) height = 190.dp else height = 170.dp
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(height) // Adjusted height for better visual balance
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
                containerColor = MaterialTheme.colorScheme.onErrorContainer // Set the background color of the card to white
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
                var showDialog by remember { mutableStateOf<Int?>(null) }
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
                        text = "Last: Ch.$comicChapter",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 3.dp, top = 10.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .offset(x = -(8).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    if (isDelete == true) {
                    IconButton(
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_library),
                                contentDescription = "add",
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                modifier = Modifier
                                    .size(15.dp)
                            )
                        },
                        onClick = { showDialog = 1 })

                        IconButton(
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_delete),
                                    contentDescription = "delete",
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                    modifier = Modifier
                                        .size(15.dp)
                                )
                            },
                            onClick = { onDeletedClicked() })
                    }
                }
                if (showDialog != null) {
                    when (showDialog) {
                        1 -> AddToLibraryDialog(
                            comicId = comicId,
                            isInComic = false,
                            isFollowed = true,
                            onDismiss = { showDialog = null })
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
}
