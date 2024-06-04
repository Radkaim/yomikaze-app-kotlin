package com.example.yomikaze_app_kotlin.Presentation.Components.TopBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CustomeAppBar(
    title: String,
    titleColor: Color = MaterialTheme.colors.onPrimary,
    titleFontSize: TextUnit = 20.sp,
    titleFontWeight: FontWeight = FontWeight.Bold,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}

) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = titleColor,
                fontSize = titleFontSize,
                fontWeight = titleFontWeight
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = MaterialTheme.colors.primary
    )
}