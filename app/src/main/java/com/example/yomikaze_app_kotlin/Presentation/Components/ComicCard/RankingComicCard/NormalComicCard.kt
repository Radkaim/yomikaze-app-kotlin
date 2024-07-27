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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.IconAndNumbers
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun NormalComicCard(
    comicId: Long,
    image: String,
    comicName: String,
    status: String,
    authorNames: List<String>,
    publishedDate: String,
    ratingScore: Float,
    follows: Long,
    views: Long,
    comments: Long,
    backgroundColor: Color = MaterialTheme.colorScheme.onErrorContainer,
    isDeleted: Boolean = false,
    isSearch: Boolean = false,
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
                            }
                    )
                }

            }
            val rowSpaceBy = if (isSearch) 45 else 15
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(rowSpaceBy.dp)
            ) {
                if (isSearch) {
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
                            .height(80.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(100.dp)
                            )
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(100.dp))
                    )
                }else
                {
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
                            .width(62.dp)
                            .height(80.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                shape = MaterialTheme.shapes.small
                            )
                            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
                    )
                }

                Column(
                    modifier = if (isSearch) {
                        Modifier.offset(x = (-20).dp)
                    } else {
                        Modifier
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.offset(y = (3).dp)
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
                        modifier = Modifier.padding(top = 5.dp)
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
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = Modifier.padding(top = 10.dp)
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
                        //Follows, Views, Comments
                        val offSetX = if (isSearch) (20) else 0

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.offset(x = offSetX.dp, y = (-6).dp)

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

private fun cutComicName(comicName: String): String {
    val comicNameLength = comicName.length
    return if (comicNameLength > 18) {
        comicName.substring(0, 18) + "..."
    } else {
        comicName
    }
}

fun processNameByComma(strings: List<String>): String {
    return strings.joinToString(", ")
}

 fun cutAuthorName(authorName: String): String {
    val authorNameLength = authorName.length
    return if (authorNameLength > 18) {
        authorName.substring(0, 18) + "..."
    } else {
        authorName
    }
}

// change Date time format
fun changeDateTimeFormat(dateTime: String): String {
    // Danh sách các định dạng đầu vào
    val inputFormatters = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSXXX"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"),
        DateTimeFormatter.ISO_DATE_TIME // định dạng ISO 8601
    )

    // Định dạng đầu ra
    val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    // Thử phân tích chuỗi ngày giờ theo từng định dạng trong danh sách
    for (inputFormatter in inputFormatters) {
        try {
            val parsedDateTime = ZonedDateTime.parse(dateTime, inputFormatter)
            return parsedDateTime.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            // Bỏ qua và thử định dạng tiếp theo
        }
    }

    // Nếu không có định dạng nào thành công, trả về chuỗi gốc
    return dateTime
}

fun changeDateTimeFormat2(dateTime: String): String {
    // Danh sách các định dạng đầu vào
    val inputFormatters = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSXXX"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"),
        DateTimeFormatter.ISO_DATE_TIME // định dạng ISO 8601
    )

    // Định dạng đầu ra
    val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

    // Thử phân tích chuỗi ngày giờ theo từng định dạng trong danh sách
    for (inputFormatter in inputFormatters) {
        try {
            val parsedDateTime = ZonedDateTime.parse(dateTime, inputFormatter)
            return parsedDateTime.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            // Bỏ qua và thử định dạng tiếp theo
        }
    }

    // Nếu không có định dạng nào thành công, trả về chuỗi gốc
    return dateTime
}