package com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.Download

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.shimmerLoadingAnimation

@Composable
fun SelectedChapterDownloadItem(
    chapterNumber: List<Int>,
    onDismiss: () -> Unit,
    onDownload: (List<Int>) -> Unit
) {
    // Remember the state of selected chapters
    val selectedChapters =
        remember { mutableStateListOf<Boolean>().apply { addAll(List(chapterNumber.size) { false }) } }


    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ) {
        items(chapterNumber.size) { index ->
            val number = chapterNumber[index]
            val isSelected = selectedChapters[index]

            Box(
                modifier = Modifier.run {
                    padding(5.dp)
                        .background(if (!isSelected) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.background)
                        .toggleable(
                            value = isSelected,
                            onValueChange = {
                                selectedChapters[index] = it
                            }
                        )
                        .shimmerLoadingAnimation()
                        .height(50.dp)
                        .width(80.dp)
                        .then(if (isSelected) Modifier.border(width = 1.dp, color = MaterialTheme.colorScheme.primary) else Modifier)
                }
            ) {
                Text(
                    text = number.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
    Button(
        modifier = Modifier
            .width(100.dp)
            .height(40.dp)
            .padding(bottom = 16.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = {
            val chaptersToDownload =
                chapterNumber.filterIndexed { index, _ -> selectedChapters[index] }
          //  Log.d("SelectedChapterDownloadItem", "chaptersToDownload: $chaptersToDownload")
            onDownload(chaptersToDownload)
            onDismiss()
        }) {
        Text(
            text = "Download",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }
}
