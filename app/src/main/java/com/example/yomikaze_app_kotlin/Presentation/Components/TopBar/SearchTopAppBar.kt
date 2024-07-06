package com.example.yomikaze_app_kotlin.Presentation.Components.TopBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchTopAppBar(
    searchText: String,
    onTextChange: (String) -> Unit,
    onCLoseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    //val focusRequester = remember { FocusRequester() }

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
            value = searchText,
            onValueChange = {
                onTextChange(it)
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
                    //modifier = Modifier.alpha(ContentAlpha.medium),
                    onClick = { onSearchClicked(searchText) }
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
                    //  modifier = Modifier.alpha(ContentAlpha.medium),
                    onClick = {
                        if (searchText.isNotEmpty()) {
                            onTextChange("")
                        } else {
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
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(searchText)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
                focusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
            ),
//            modifier = Modifier
//                .focusRequester(focusRequester)
//                .onFocusChanged { focusState ->
//                    if (!focusState.isFocused) {
//                        keyboardController?.hide() // Hide the keyboard when the text field loses focus
//                    }
//                }
        )
    }
}


