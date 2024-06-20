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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
                fontSize = returnTextSizeSelected(isOldestSelected = isOldestSelected).sp,
                color = returnColorSelected(isOldestSelected = isOldestSelected),
                fontWeight = returnFontWeightSelected(isOldestSelected = isOldestSelected),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onOldSortClick() }
                    .then(Modifier)

            )
            // devide line straigt line
            Divider(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.36f),
                modifier = Modifier
                    .height(27.dp)
                    .width(1.dp)
                    .padding(top = 8.dp)
            )
            //Sort by new
            Text(
                text = "Newest",
                fontSize = returnTextSizeSelected(isOldestSelected = !isOldestSelected).sp,
                color = returnColorSelected(isOldestSelected = !isOldestSelected),
                fontWeight = returnFontWeightSelected(isOldestSelected = !isOldestSelected),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onnNewSortClick() }
                    .then(Modifier)
            )

        }

    }
}

@Composable
fun returnTextSizeSelected(isOldestSelected: Boolean): Float {
    return if (isOldestSelected) 14f else 12f
}

@Composable
fun returnColorSelected(isOldestSelected: Boolean): Color {
    return if (isOldestSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
}

@Composable
fun returnFontWeightSelected(isOldestSelected: Boolean): FontWeight {
    return if (isOldestSelected) FontWeight.Bold else FontWeight.Normal
}

@Composable
fun returnShadowModifier(isOldestSelected: Boolean): Modifier {
    return if (isOldestSelected) Modifier.shadow(
        elevation = 2.dp,

    ) else Modifier
}
