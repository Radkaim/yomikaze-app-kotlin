package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
@Composable
fun CardComicWeeklyHome(
    image: String,
    comicName: String,
    comicAuth: String
) {
    Surface(
        modifier = Modifier
            .height(230.dp)
            .width(115.dp)
            .padding(bottom = 20.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.tertiary
    ) {

        Column(
            modifier = Modifier
//                .padding(5.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(115.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comicName,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis

            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comicAuth,
                fontSize = 11.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}