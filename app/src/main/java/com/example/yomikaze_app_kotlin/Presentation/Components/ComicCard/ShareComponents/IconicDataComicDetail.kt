package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IconicDataComicDetail(
    icon: Int,
    iconColor: Color,
    iconWidth: Int? = null,
    iconHeight: Int? = null,
    numberRating: Float? = null,
    number: Long = 0,
    numberColor: Color,
    numberWeight: FontWeight = FontWeight.Normal,
    numberSize: Int = 14,
    title: String,
) {
    // Assign the icon width and height if they are not null
    val finalIconWith = iconWidth ?: 24
    val finalIconHeight = iconHeight ?: 24

    Box {
        Column {
            Text(
                text = numberRating?.toString() ?: changeTextFormat(number!!),
                color = numberColor,
                fontSize = numberSize.sp,
                fontWeight = numberWeight,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 3.dp)
            )

            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier
                        .width(finalIconWith.dp)
                        .height(finalIconHeight.dp)
                )

                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}
