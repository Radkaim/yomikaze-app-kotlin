package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.AboutUs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AboutUsObject(
    val title: String,
    val titleEnd: String,
    val iconEnd: Int,
)

@Composable
fun AboutUsItem(
    title: String,
    titleEnd: String,
    iconEnd: Int,
    onClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.padding(start = 5.dp)
        )

        if (titleEnd.isNotEmpty()) {
            Text(
                text = titleEnd,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W300
                ),
                color = MaterialTheme.colorScheme.primaryContainer
            )
        } else {
            IconButton(
                modifier = Modifier.size(10.dp),
                onClick = { onClicked()  }) {
                Icon(
                    painter = painterResource(id = iconEnd),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f),
                    modifier = Modifier.size(16.dp)
                )
            }

        }
    }
    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.1f),
        thickness = 1.dp,
    )
}