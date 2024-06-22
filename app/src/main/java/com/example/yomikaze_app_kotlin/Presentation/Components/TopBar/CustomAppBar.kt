@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.yomikaze_app_kotlin.Presentation.Components.TopBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.primaryContainer,
    titleFontSize: TextUnit = 20.sp,
    titleFontWeight: FontWeight = FontWeight.Medium,
    textAlign: TextAlign = TextAlign.Center,
    navigationIcon: @Composable () -> Unit ,
    actions: @Composable RowScope.() -> Unit = {}

) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor =
           MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        title = {
            Text(
                text = title,
                color = titleColor,
                fontSize = titleFontSize,
                fontWeight = titleFontWeight,
                textAlign = textAlign
            )
            Modifier.padding(
                start = 160.dp,
                end = 16.dp
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
    )
}