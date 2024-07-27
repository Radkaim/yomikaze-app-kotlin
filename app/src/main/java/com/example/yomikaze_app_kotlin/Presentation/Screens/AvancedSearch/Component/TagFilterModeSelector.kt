package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.AdvancedSearchViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Mode

@Composable
fun TagFilterModeSelector(
    viewModel: AdvancedSearchViewModel
) {
    val inclusionModeOptions = listOf("And", "Or")
    val exclusionModeOptions = listOf("And", "Or")
    val state by viewModel.state.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Inclusion Mode
        Column {

            Text(
                text ="Inclusion Mode:",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.primaryContainer,
                textDecoration = TextDecoration.Underline
                )
            Row(
                modifier = Modifier.offset(x = -(15).dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                inclusionModeOptions.forEach { mode ->
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (state.queryInclusionMode?.name == mode),
                            onClick = {
                                if (state.queryInclusionMode?.name == mode) return@RadioButton
                                viewModel.updateQueryInclusionMode(Mode.valueOf(mode))
                            }
                        )
                        Text(mode.uppercase())
                    }
                }
            }
        }

        // Exclusion Mode
        Column{
            Text(
                text ="Exclusion Mode:",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.primaryContainer,
                textDecoration = TextDecoration.Underline
            )
            Row(
                modifier = Modifier.offset(x = -(15).dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                exclusionModeOptions.forEach { mode ->
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (state.queryExclusionMode?.name == mode),
                            onClick = {
                                viewModel.updateQueryExclusionMode(Mode.valueOf(mode))
                            }
                        )
                        Text(mode.uppercase())
                    }
                }
            }
        }
    }
}
