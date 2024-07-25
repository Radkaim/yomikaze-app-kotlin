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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.LibraryViewModel
import com.example.yomikaze_app_kotlin.R

/**
 * Dialog for creating a new category
 */
@Composable
fun CreateCategoryDialog(
    libraryViewModel: LibraryViewModel,
    onDismiss: () -> Unit
) {
    var categoryName by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    //get state of libraryViewModel
    val state by libraryViewModel.state.collectAsState()

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
                        text = "Create new personal category",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Name for personal category",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(top = 2.dp)
                    )



                    Spacer(modifier = Modifier.height(8.dp))

                    //text field for category name
                    TextField(
                        value = categoryName,
                        onValueChange = { categoryName = it },
                        label = {
                            if (isError && categoryName.isEmpty()) {
                                Text(
                                    text = "Category Name is required",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                                if (categoryName.length > 32) {
                                    isError = true
                                    Text(
                                        text = "Category Name is too long",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }else {
                                    //label for text field (category name
                                    if (categoryName.isNotEmpty() || !isError) {
                                        isError = false
                                        Text(
                                            text = "Category Name",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done // Đặt hành động IME thành Done
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_email),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-10).dp)
                            .height(56.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = MaterialTheme.colorScheme.surfaceTint,
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                            cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),

                            //error color
                            errorIndicatorColor = MaterialTheme.colorScheme.error,
                            errorLabelColor = MaterialTheme.colorScheme.error
                        ),
                        isError = isError
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {

                        //button to create category
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(text = "Dismiss")
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(
                            onClick = {
                                if (categoryName.isEmpty() || categoryName.length > 32) {
                                    isError = true
                                } else {
                                    libraryViewModel.addNewCategory(categoryName)
                                    if (state.isCreateCategorySuccess) {
                                        onDismiss()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onSecondary,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Text(text = "Create")
                        }
                    }
                }
            }
        }
    }
}
