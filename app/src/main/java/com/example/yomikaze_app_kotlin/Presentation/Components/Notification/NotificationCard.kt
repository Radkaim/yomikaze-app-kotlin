package com.example.yomikaze_app_kotlin.Presentation.Components.Notification

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.changeDateTimeFormat
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.R

@Composable
fun NotificationCard(
    title: String,
    content: String,
    isRead: Boolean,
    creationTime: String,
    onClicked: () -> Unit? = {}
) {
    Surface(
        modifier = Modifier
            .width(370.dp)
            .height(70.dp)
            .offset(x = 10.dp)
            .clickable(onClick = { onClicked() }),
        color = MaterialTheme.colorScheme.onErrorContainer,
        shape = RoundedCornerShape(20), // Making it oval
        shadowElevation = 5.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    shape = RoundedCornerShape(20)
                )
                .clickable {
                    onClicked()
                }
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //for notification title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .padding(start = 20.dp)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "Notification Icon",
                        tint = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier
                            .size(20.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),

                            ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Box(
                                // contentAlignment = Alignment.Center,
                                modifier = Modifier.offset(y = (-8).dp)
                            ) {
                                if (!isRead) {
                                    TagComponent(
                                        status = "Unread",
                                        backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(
                                            alpha = 0.8f
                                        ),
                                    )
                                }
                            }

                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()

                            ) {
                            Text(
                                text = content,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.width(200.dp)
                            )
                            Text(
                                text = "Date: " + changeDateTimeFormat(creationTime),
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(end= 20.dp)
                            )
                        }


                    }


                }
            }
        }
    }

}
