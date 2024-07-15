package com.example.yomikaze_app_kotlin.Presentation.Components.Dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter

@Composable
fun UnlockChapterDialogComponent(
    title: String, // title of dialog
    totalCoin: Long, // total coin for unlock
    coinOfUserAvailable: Long, // coin of user available
    chapter: Chapter,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit
) {
    //get context
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
                .fillMaxWidth()
                .background(Color.Gray.copy(alpha = 0.7f)) // Màu xám với độ mờ
                .clickable { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .height(180.dp)
                    .width(380.dp)
                    .align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 5.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                    Box {
                        Text(
                            text = "chapter 1: the beginning of the end of the world and the beginning of the end of the world",
                            fontSize = 12.sp,
                            maxLines = 2,
                            lineHeight = 16.sp,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                            fontStyle = FontStyle.Italic,
                            overflow = TextOverflow.Ellipsis,
                            softWrap = true,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start = 40.dp, top = 3.dp)
                                .align(Alignment.Center)
                                .width(250.dp)
                        )

                    }

                    Text(
                        text = buildAnnotatedString {
                            append("Using ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            ) {
                                append("$totalCoin coins")
                            }
                            append(" to unlock")
                        },
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 80.dp, top = 5.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("Available: ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            ) {
                                append("$coinOfUserAvailable")
                                append(if (coinOfUserAvailable > 1) " coins" else " coin")
                            }
                            append(" in your account")
                        },

                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.run { padding(start = 60.dp, top = 10.dp) }
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(text = "Cancel")
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Button(
                            onClick = {
                                onConfirmClick()
                                onDismiss()
//                                viewModel.delete(key, isDeleteAll)
//                                if (viewModel.isDeleteSuccess!!) {
//                                    Toast.makeText(
//                                        context,
//                                        "Delete successfully",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    onDismiss()
//                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onSecondary,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Text(text = "Unlock")
                        }
                    }
                }
            }
        }
    }
}