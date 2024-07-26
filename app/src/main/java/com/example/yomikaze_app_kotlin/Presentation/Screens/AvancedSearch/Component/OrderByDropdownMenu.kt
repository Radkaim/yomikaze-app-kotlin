package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.AdvancedSearchViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.OrderBy

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderByDropdownMenu(
    viewModel: AdvancedSearchViewModel
) {
    val orderByOptions = OrderBy.values().toList()
    val orderByState by viewModel.state.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = orderByState.selectedOrderBy.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            ),
//            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            orderByOptions.forEach { orderBy ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.updateOrderBy(orderBy)
                        expanded = false
                    }
                ) {
                    Text(orderBy.name)
                }
            }
        }
    }
}

