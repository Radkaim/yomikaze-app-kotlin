package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.AdvancedSearchViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.OrderBy

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderByOptionsDropdownMenu(
    viewModel: AdvancedSearchViewModel,
) {
    val optionsOrderBySelect = OrderBy.values().toList()
    val state by viewModel.state.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp)
            .offset(x = 30.dp)
    ) {
        OutlinedTextField(
            value = orderByDisplayName(state.selectedOrderBy.name),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = "DropDownIcon",
                    modifier = Modifier.size(30.dp)
                )
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
//            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            optionsOrderBySelect.forEach { orderBy ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.updateOrderBy(orderBy)
                        expanded = false
                    }
                ) {
                    Text(orderByDisplayName(orderBy.name))
                }
            }
        }
    }
}

// change orderBy.name format to orderBy.displayName
fun orderByDisplayName(name: String): String {
    //space to camel case
    // Tách các từ dựa trên chữ cái viết hoa và nối chúng lại với nhau bằng khoảng trắng
    return name.split("(?=[A-Z])".toRegex())
        .joinToString(" ") { it.capitalize() }
}
