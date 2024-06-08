package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RankingNumberCircle(
    number: Int,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary,
    borderColor: Color = MaterialTheme.colorScheme.onPrimary,
    size: Dp = 24.dp,
    fontSize: TextUnit = 10.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    textAlign: TextAlign = TextAlign.Center
) {
    Surface(
        shape = CircleShape,
        color = Color.Red,
        modifier = Modifier
            .size(size)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(backgroundColor)
                .size(size)
                .size(size)
        ) {
            Text(
                text = number.toString(),
                color = MaterialTheme.colorScheme.background,
                fontWeight = fontWeight,
                fontSize = fontSize,
                textAlign = textAlign
            )
        }
    }
}

@Composable
@Preview
fun RankingNumberCirclePreview() {
    RankingNumberCircle(
        number = 1,
    )
}
