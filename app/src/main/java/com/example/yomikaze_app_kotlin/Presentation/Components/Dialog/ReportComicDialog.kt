package com.example.yomikaze_app_kotlin.Presentation.Components.Dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ReportComicDialog(
    title: String,
    value: String,
    onSubmit: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    var value by remember { mutableStateOf((value)) }
    var isError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val textSize = 512

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
                    .height(280.dp)
                    .width(380.dp)

                    .align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = value,
                        onValueChange = { newText ->
                            value = newText
                        },

                        label = {
                            if (value.length > textSize) {
                                isError = true
                                Text(
                                    text = "Your comment is too long",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.error
                                )
                            } else if (value.isEmpty()) {
                                isError = true
                                Text(
                                    modifier = Modifier.alpha(ContentAlpha.medium),
                                    text = "Say something...",
                                    fontWeight = FontWeight.Medium,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.errorContainer
                                )
                            } else {
                                isError = false
                                Text(
                                    modifier = Modifier.alpha(ContentAlpha.medium),
                                    text = "",
                                    fontWeight = FontWeight.Medium,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.errorContainer
                                )

                            }
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        trailingIcon = {
                            IconButton(
                                //  modifier = Modifier.alpha(ContentAlpha.medium),
                                onClick = {
                                    if (value.isNotEmpty()) {
                                        value = ("")
                                    } else {
                                        focusManager.clearFocus() // Clear focus to hide the keyboard
                                        keyboardController?.hide() // Hide the keyboard
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close",

                                    )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = if (value.isNotEmpty()) ImeAction.Done else ImeAction.Default
                        ),
                        keyboardActions = KeyboardActions(
                            // enter a new line when press enter
//                            onDone = {
//                                if (value.isEmpty()) {
//                                    focusManager.clearFocus() // Clear focus to hide the keyboard
//                                    keyboardController?.hide() // Hide the keyboard
//                                }
//                            }
                        ),
                        isError = value.length > textSize,   //check if the length of text is over 256 characters
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            backgroundColor = MaterialTheme.colorScheme.onErrorContainer,
                            focusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                            cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            trailingIconColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.7f),
                            //for error
                            errorCursorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            errorIndicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            errorLabelColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            errorTrailingIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        ),
                        maxLines = 5,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(30.dp)
                            )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {

                        Spacer(modifier = Modifier.width(20.dp))
                        Button(
                            onClick = {
                                if (value.isEmpty() || value.length > textSize) {
                                    isError = true
                                } else {
                                    onSubmit(value)
                                    onDismiss()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onSecondary,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }
    }
}