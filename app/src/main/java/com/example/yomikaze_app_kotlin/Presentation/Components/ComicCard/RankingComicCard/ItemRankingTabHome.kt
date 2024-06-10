package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemRankingTabHome(
    tabName: String,
    textSize: TextUnit = 12.sp,
    isSelected : Boolean = false,
    textColor: Color = MaterialTheme.colorScheme.background,
    modifier: Modifier
) {
    // TODO: Implement ComicStatus
    val color: Color
    if (isSelected) {
        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
    } else {
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.36f)
    }

    Surface(
        modifier = Modifier.wrapContentWidth().wrapContentHeight().then(modifier),
        color = color,
        shape = RoundedCornerShape(50), // Making it oval
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(
                start = 5.dp,
                end = 5.dp,
                top = 2.dp,
                bottom = 2.dp
            )
        ) {
            Text(
                text = tabName,
                color = textColor,
                fontSize = textSize,
                textAlign = TextAlign.Center
            )
        }
    }
}