package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SortComponent(
    isOldestSelected: Boolean,
    onnNewSortClick: () -> Unit,
    onOldSortClick: () -> Unit,
) {
    Box {
        Row {
            //Sort by old
            Text(
                text = "Oldest",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onOldSortClick() }
            )
            // devide line straigt line
            Divider(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.36f),
                modifier = Modifier
                    .height(30.dp)
                    .width(1.dp)
                    .padding(top = 10.dp)
            )
            //Sort by new
            Text(
                text = "Newest",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onnNewSortClick() }
            )

        }

    }

}