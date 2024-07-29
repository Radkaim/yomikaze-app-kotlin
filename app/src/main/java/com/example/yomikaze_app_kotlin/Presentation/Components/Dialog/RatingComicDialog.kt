package com.example.yomikaze_app_kotlin.Presentation.Components.Dialog

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailState
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailViewModel
import com.example.yomikaze_app_kotlin.R


@Composable
fun RatingComicDialog(
    comicId: Long,
    state: ComicDetailState,
    comicDetailViewModel: ComicDetailViewModel,
    onDismiss: () -> Unit
) {
    // Remember the state of the stars
    val starState = remember { mutableStateListOf(false, false, false, false, false) }
    val context = LocalContext.current
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.7f)) // Màu xám với độ mờ
                .offset(y = (100).dp)
                .clickable { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .width(200.dp)
                    .height(137.dp)
                    .align(Alignment.Center)
                    .offset(y = (-100).dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Rating Comic",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onError,
                            textAlign = TextAlign.Center

                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Display stars
                        starState.forEachIndexed { index, isSelected ->
                            Icon(
                                painter = painterResource(id = if (isSelected) R.drawable.ic_star_fill else R.drawable.ic_star),
                                contentDescription = "Star $index",
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable {
                                        for (i in 0..index) {
                                            starState[i] = true
                                        }
                                        for (i in index + 1 until starState.size) {
                                            starState[i] = false
                                        }
                                    },
                                tint = if (isSelected) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surface
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {

                        Button(
                            modifier = Modifier
                                .width(80.dp)
                                .height(30.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colorScheme.onSecondary,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            onClick = {
                                val selectedStars = starState.count { it }
                                comicDetailViewModel.rateComic(comicId, selectedStars)
                                Log.d("RatingComicDialog", "Selected stars: $selectedStars")
                                if (state.isRatingComicSuccess) {
                                    Toast.makeText(
                                        context,
                                        "Rating successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onDismiss()
                                } else {
                                    onDismiss()
                                }
//
                            }) {
                            Text(
                                text = "Vote",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFffF2FFFD)
                            )
                        }
                    }
                }
            }
        }
    }
}
