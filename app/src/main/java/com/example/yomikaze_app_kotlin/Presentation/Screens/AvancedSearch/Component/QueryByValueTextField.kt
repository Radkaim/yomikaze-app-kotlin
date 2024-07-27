package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun QueryByValueTextField(
    placeHolderTitle: String,
    queryByValue: String,
    onValueChange: (String) -> Unit,
    onCLoseClicked: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val imeAction = remember { mutableStateOf(ImeAction.Done) }




    OutlinedTextField(
        value = queryByValue,
        onValueChange = {
            onValueChange(it) // Cập nhật giá trị của TextField
//                imeAction.value = ImeAction.Search
        },
        label = { Text(placeHolderTitle) },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        singleLine = true,
//            leadingIcon = {
//                if (queryByValue.isNotEmpty()) {
//                    IconButton(
//                        onClick = { onValueChange("") }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.Search,
//                            contentDescription = "Search",
//                            tint = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.7f)
//                        )
//                    }
//                }
//            },
        trailingIcon = {
            if (queryByValue.isNotEmpty()) {
                IconButton(
                    onClick = {
                        if (queryByValue.isNotEmpty()) {
                            Log.d("queryByComicName", "queryByComicName: $queryByValue")
                            onValueChange("")
                        } else {
                            Log.d("queryByComicName", "queryByComicName: $queryByValue")
                            onCLoseClicked()
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
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp, end = 65.dp)
            .offset(x = 10.dp),

        keyboardOptions = KeyboardOptions(
            imeAction = imeAction.value
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
//                    onValueChange(queryByComicName)
                imeAction.value = ImeAction.Done
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
            trailingIconColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
            focusedLabelColor = MaterialTheme.colorScheme.surface,
            unfocusedLabelColor = MaterialTheme.colorScheme.surface.copy(alpha = ContentAlpha.medium)
        ),
    )
}
