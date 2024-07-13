package com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.Download

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.R

@Composable
fun NormalChapterDownload(
    orderIndex: Int,
    chapterNumber: Int,
    totalMbs: Long,
    isDownloaded: Boolean,
    isInSelectionMode: Boolean,
    isSelected: Boolean,
    titleColor: Color = MaterialTheme.colorScheme.primaryContainer,
    downloadStatusColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClicked: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClicked() }
    ) {
        val arrangement = if (isInSelectionMode) {
            Arrangement.SpaceBetween
        } else {
            Arrangement.SpaceBetween
        }
        Row(
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 30.dp)
        ) {
            Box {
                if (isInSelectionMode) {
                    val icon = if (isSelected) {
                        R.drawable.ic_choose_circle_tick
                    } else {
                        R.drawable.ic_choose_circle
                    }
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .offset(x = -20.dp)
                            .width(17.dp)
                            .height(17.dp),
                    )
                }

                val offset = if (isInSelectionMode) 10 else -16
                Row {
                    Text(
                        text = "$orderIndex.",
                        color = titleColor,
                        modifier = Modifier.offset(x = offset.dp)
                    )

                    Text(
                        text = "Chapter $chapterNumber",
                        color = titleColor,
                        fontSize = 15.sp,
                        modifier = Modifier.offset(x = offset.dp)
                    )
                }

            }
            val offset = if (isInSelectionMode) -20 else 0
            Text(
                text = convertToMbsOrGbs(totalMbs),
                color = downloadStatusColor,
                fontSize = 13.sp,
                modifier = Modifier.offset(x = offset.dp)
            )

            if (isInSelectionMode) {
//                Text(
//                    text = if (isSelected) "Selected" else "Select",
//                    color = downloadStatusColor,
//                    style = returnStyleForDownloadStatus(),
//                    modifier = Modifier.padding(end = 20.dp)
//                )
            } else {
                Text(
                    text = if (isDownloaded) "Downloaded" else "Pending",
                    color = downloadStatusColor,
                    style = returnStyleForDownloadStatus(),
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
        }
    }

}

@Composable
fun returnStyleForDownloadStatus(): TextStyle {
    return TextStyle(
        color = MaterialTheme.colorScheme.secondaryContainer,
        fontWeight = TextStyle.Default.fontWeight,
        fontSize = 10.sp

    )
}


fun convertToMbsOrGbs(kbs: Long): String {
    if (kbs < 1024) return "$kbs KB" // KB
    if (kbs < 1024 * 1024) return "${kbs / 1024} MB" // MB
    return "${kbs / (1024 * 1024)} GB" // GB
}