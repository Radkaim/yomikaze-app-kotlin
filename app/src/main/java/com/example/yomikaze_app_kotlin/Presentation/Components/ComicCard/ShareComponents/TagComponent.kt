package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TagComponent(
    status: String,
    textSize: TextUnit = 12.sp,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceTint.copy(0.65f),
    textColor: Color = MaterialTheme.colorScheme.onSurface,

) {
    // TODO: Implement ComicStatus
    Surface(
        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
        color = backgroundColor,
        shape = RoundedCornerShape(50) // Making it oval
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
                text = status,
                color = textColor,
                fontSize = textSize,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun ComicStatusPreview() {
    TagComponent(status = "Completed")
}