package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun QueryByComicNameTextField(
    queryByComicName: String,
    onValueChange: (String) -> Unit,
    onCLoseClicked: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val imeAction = remember { mutableStateOf(ImeAction.Done) }


    Surface(
        modifier = Modifier
            .height(56.dp)
            .width(390.dp)
            .padding(start = 5.dp)
            .background(MaterialTheme.colorScheme.tertiary),

        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
    )
    {
        TextField(
            value = queryByComicName,
            onValueChange = {
                onValueChange(it) // Cập nhật giá trị của TextField
//                imeAction.value = ImeAction.Search
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = { onValueChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.7f)
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (queryByComicName.isNotEmpty()) {
                            Log.d("queryByComicName", "queryByComicName: $queryByComicName")
                            onValueChange("")
                        } else {
                            Log.d("queryByComicName", "queryByComicName: $queryByComicName")
                            onCLoseClicked()
                            focusManager.clearFocus() // Clear focus to hide the keyboard
                            keyboardController?.hide() // Hide the keyboard
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.7f)
                    )
                }
            },

            keyboardOptions = KeyboardOptions(
                imeAction = imeAction.value
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
//                    onValueChange(queryByComicName)
                    imeAction.value = ImeAction.Done
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
                focusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
            ),
        )
    }
}
