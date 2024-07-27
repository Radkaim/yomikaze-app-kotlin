package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.yomikaze_app_kotlin.R

@Composable
fun AuthorsManager(
    authors: List<String>?,
    onAddAuthor: (String) -> Unit,
    onRemoveAuthor: (String) -> Unit
) {
    var newAuthor by remember { mutableStateOf("") }
    val imeAction = remember { mutableStateOf(ImeAction.Send) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp, end = 65.dp)

    ) {
        // Phần nhập tên tác giả mới
        OutlinedTextField(
            value = newAuthor,
            onValueChange = { newAuthor = it },
            label = { Text("Add new author name") },
            trailingIcon = {
                IconButton(onClick = {
                    if (newAuthor.isNotBlank()) {
                        onAddAuthor(newAuthor)
                        newAuthor = ""
                    }
                }) {
                    if (newAuthor.isNotBlank()) {
                        Icon(
                            painterResource(id = R.drawable.ic_plus),
                            contentDescription = "Add author",
                            modifier = Modifier
                                .size(15.dp)
                                .offset(x = -(10).dp)
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = if (newAuthor.isNotBlank()) imeAction.value else ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (newAuthor.isNotBlank()) {
                        onAddAuthor(newAuthor)
                        newAuthor = ""
                    }
                },
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
                focusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                trailingIconColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.7f),
                focusedLabelColor = MaterialTheme.colorScheme.surface,
                unfocusedLabelColor = MaterialTheme.colorScheme.surface.copy(alpha = ContentAlpha.medium)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp)
                .offset(x = 10.dp)
        )

        // Phần danh sách tác giả
        if (authors != null) {
            authors.forEach { author ->
                OutlinedTextField(
                    value = author,
                    readOnly = true,
                    onValueChange = {},
                    label = { Text("Author") },
                    trailingIcon = {
                        IconButton(onClick = { onRemoveAuthor(author) }) {
                            Icon(
                                painterResource(id = R.drawable.ic_delete),
                                contentDescription = "Remove author",
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                        cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        trailingIconColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        focusedLabelColor = MaterialTheme.colorScheme.surface,
                        unfocusedLabelColor = MaterialTheme.colorScheme.surface.copy(alpha = ContentAlpha.medium)
                    ),
                    modifier = Modifier
                        .width(280.dp)
                        .align(Alignment.End)
                        .padding(top = 5.dp, bottom = 5.dp)
                        .offset(x = (10).dp)
                )
            }
        }
    }

}
