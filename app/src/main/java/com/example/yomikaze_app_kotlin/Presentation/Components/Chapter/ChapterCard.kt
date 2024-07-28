package com.example.yomikaze_app_kotlin.Presentation.Components.Chapter

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.changeDateTimeFormat
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.IconAndNumbers
import com.example.yomikaze_app_kotlin.R

@Composable
fun ChapterCard(
    chapterNumber: Int,
    title: String,
    views: Long,
    comments: Long,
    publishedDate: String,
    isLocked: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.onErrorContainer,
    onReportClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(backgroundColor)
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                //shape = MaterialTheme.shapes.small
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(backgroundColor)
                .clickable { onClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (isLocked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Lock",
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                            .padding(start = 10.dp, top = 2.dp)
                    )
                }
                val startDp: Int = if (isLocked) 16 else 40
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = startDp.dp, top = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(backgroundColor)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row() {
                                Text(
                                    text = "Chapter ${chapterNumber +1}: $title",
                                    fontSize = 13.sp,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = changeColorWhenLocked(
                                        isLocked = isLocked,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    ),
                                    overflow = TextOverflow.Ellipsis,
                                    lineHeight = 15.sp,
                                    modifier = Modifier.width(300.dp)
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_report),
                                    contentDescription = "Report",
                                    tint = changeColorWhenLocked(
                                        isLocked = isLocked,
                                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.9f)
                                    ),
                                    modifier = Modifier
                                        .width(15.dp)
                                        .height(15.dp)
                                        .offset(x = 20.dp, y = (-7).dp)
                                        .clickable { onReportClick() })
                            }
                        }
                        // for views and comments and published date
                        val spaceByofBoxAndText: Int = if (isLocked) 100 else 100
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = (-4).dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(spaceByofBoxAndText.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(120.dp)
                                ) {
                                    //View
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                                    ) {
                                        IconAndNumbers(
                                            icon = R.drawable.ic_eye,
                                            iconColor = changeColorWhenLocked(
                                                isLocked = isLocked,
                                                color = MaterialTheme.colorScheme.onTertiary.copy(
                                                    alpha = 0.7f
                                                )
                                            ),
                                            number = views,
                                            numberColor = changeColorWhenLocked(
                                                isLocked = isLocked,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                            ),
                                            numberWeight = FontWeight.Light,
                                            iconWidth = 15,
                                            iconHeight = 10,
                                            numberSize = 10
                                        )
                                        //Comments
                                        IconAndNumbers(
                                            icon = R.drawable.ic_comment,
                                            iconColor = changeColorWhenLocked(
                                                isLocked = isLocked,
                                                color = MaterialTheme.colorScheme.onTertiary.copy(
                                                    alpha = 0.7f
                                                )
                                            ),
                                            number = comments,
                                            numberColor = changeColorWhenLocked(
                                                isLocked = isLocked,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                            ),
                                            numberWeight = FontWeight.Light,
                                            iconWidth = 10,
                                            iconHeight = 12,
                                            numberSize = 10
                                        )
                                    }

                                }

                                //Published Date
                                Text(
                                    text = "Create date: ${changeDateTimeFormat(publishedDate)}",
                                    fontSize = 10.sp,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Light,
                                        fontStyle = FontStyle.Italic,
                                    ),
                                    color = changeColorWhenLocked(
                                        isLocked = isLocked,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    ),
                                    modifier = Modifier.padding(top = 11.dp)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun changeColorWhenLocked(isLocked: Boolean, color: Color): Color {
    return if (isLocked) {
        color.copy(alpha = 0.5f)
    } else {
        color
    }
}