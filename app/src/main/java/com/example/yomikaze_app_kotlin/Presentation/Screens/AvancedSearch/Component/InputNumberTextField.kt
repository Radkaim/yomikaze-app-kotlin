package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputNumberTextField(
    onValueFromChange: (Int) -> Unit,
    onValueToChange: (Int) -> Unit,
    // default value for TextField
    defaultValueFrom: String? = "",
    defaultValueTo: String? = "",
    isReset: Boolean = false,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {
    val context = LocalContext.current
    // Declaring a string variable
    // to store the TextField input



    var mTextFrom by remember { mutableStateOf(if(defaultValueFrom != null) defaultValueFrom.toString() else "") }
    var mTextTo by remember { mutableStateOf(if (defaultValueTo != null) defaultValueTo.toString() else "") }

    //reset TextField
    if (isReset) {
        mTextFrom = ""
        mTextTo = ""
        mTextFrom = remember {
            mutableStateOf("")
        }.value
        mTextTo = remember {
            mutableStateOf("")
        }.value
    }

    Row(
        modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(80.dp)
    ) {
        // TextField for input number from value
        TextField(
            value = mTextFrom,
            onValueChange = { mTextFrom = it },
            placeholder = {
                androidx.compose.material3.Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "From: ",
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                // enter a new line when press enter
                onDone = {
                    if (mTextFrom.isNotEmpty() && mTextFrom.toIntOrNull() != null) {
                        onValueFromChange(mTextFrom.toInt())
                        focusManager.clearFocus() // Clear focus to hide the keyboard
                        keyboardController?.hide() // Hide the keyboard
                    } else {
                        Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            ),
            modifier = Modifier
                .width(120.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(10.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
                focusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
            ),
        )
        // TextField for input number to value
        TextField(
            value = mTextTo,
            onValueChange = { mTextTo = it },
            placeholder = {
                androidx.compose.material3.Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "To: ",
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                // enter a new line when press enter
                onDone = {
                    // check if the TextField is not empty and not is a string

                    if (mTextTo.isNotEmpty() && mTextTo.toIntOrNull() != null) {
                        onValueToChange(mTextTo.toInt())
                        focusManager.clearFocus() // Clear focus to hide the keyboard
                        keyboardController?.hide() // Hide the keyboard
                    } else {
                        Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            ),
            modifier = Modifier
                .width(120.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(10.dp)
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