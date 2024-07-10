package com.example.yomikaze_app_kotlin.Presentation.Components.Dialog

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Base.StatefulViewModel

@Composable
fun <T, VM> DeleteConfirmDialogComponent(
    key: Long,
    title: String, // title of dialog
    value: String?, //name
    viewModel: VM,
    onDismiss: () -> Unit,
    isDeleteAll: Boolean? = null
) where VM : ViewModel, VM : StatefulViewModel<T> {

    var value by remember { mutableStateOf(value) }
    var isError by remember { mutableStateOf(false) }
    //get state of libraryViewModel
    val state by viewModel.state.collectAsState()

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
                        .padding(start = 20.dp, top = 10.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        //   modifier = Modifier.padding(start = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    //text field for category name
//                    TextField(
//                        value = value,
//                        onValueChange = { value = it },
//                        label = {
//                            if (isError && value.isEmpty()) {
//                                Text(
//                                    text = "Category Name is required",
//                                    fontSize = 12.sp,
//                                    color = MaterialTheme.colorScheme.error
//                                )
//                            }
//                            if (value.length > 32) {
//                                isError = true
//                                Text(
//                                    text = "Category Name is too long",
//                                    fontSize = 12.sp,
//                                    color = MaterialTheme.colorScheme.error
//                                )
//                            }else {
//                                //label for text field (category name
//                                if (value.isNotEmpty() || !isError) {
//                                    isError = false
//                                    Text(
//                                        text = "Category Name",
//                                        fontSize = 12.sp,
//                                        color = MaterialTheme.colorScheme.primary
//                                    )
//                                }
//                            }
//                        },
//                        maxLines = 1,
//                        keyboardOptions = KeyboardOptions.Default.copy(
//                            imeAction = ImeAction.Done // Đặt hành động IME thành Done
//                        ),
//                        leadingIcon = {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_email),
//                                contentDescription = "",
//                                tint = MaterialTheme.colorScheme.primaryContainer,
//                                modifier = Modifier
//                                    .width(20.dp)
//                                    .height(20.dp),
//                            )
//                        },
//
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
//                            .border(
//                                width = 1.dp,
//                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
//                                shape = MaterialTheme.shapes.large,
//
//                                )
//                            .height(56.dp)
//                            .background(
//                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
//                                shape = RoundedCornerShape(20.dp),
//                            ),
//                        shape = RoundedCornerShape(24.dp),
//                        colors = TextFieldDefaults.textFieldColors(
//                            textColor = MaterialTheme.colorScheme.surfaceTint,
//                            backgroundColor = Color.Transparent,
//                            focusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
//                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
//                            cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
//
//                            //error color
//                            errorIndicatorColor = MaterialTheme.colorScheme.error,
//                            errorLabelColor = MaterialTheme.colorScheme.error
//                        ),
//                        isError = isError
//                    )
                    if (value!!.isNotEmpty()) {
                        Row(
                            modifier = Modifier.padding(start = 40.dp)
                        ) {
                            Text(
                                text = "Name: ",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.inverseSurface,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = value ?: "",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                            )

                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = {
                                viewModel.delete(key, isDeleteAll)
                                if (viewModel.isDeleteSuccess!!) {
                                    Toast.makeText(
                                        context,
                                        "Delete successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onDismiss()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onSecondary,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Text(text = "Confirm")
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(text = "Cancel")
                        }

                    }
                }
            }
        }
    }
}