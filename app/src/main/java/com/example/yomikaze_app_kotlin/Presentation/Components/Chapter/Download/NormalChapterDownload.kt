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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        Row(
            //modifier = Modifier
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 30.dp)
        ) {
            Box {
                Text(
                    text = "$orderIndex.",
                    color = titleColor,
                    modifier = Modifier.offset(x = (-16).dp)
                )

                Text(
                    text = "Chapter $chapterNumber",
                    color = titleColor,
                    fontSize = 15.sp
                )
            }

            Text(
                text = "$totalMbs KB",
                color = downloadStatusColor,
                fontSize = 13.sp
            )

            if (isDownloaded) {
                Text(
                    text = "Downloaded",
                    color = downloadStatusColor,
                    style = returnStyleForDownloadStatus(),
                    modifier = Modifier.padding(end = 20.dp)


                )
            } else {
                Text(
                    text = "Pending",
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
        fontSize = 9.sp

    )
}


fun convertToMbs(kbs: Double): Double {
    return kbs / 1024
}