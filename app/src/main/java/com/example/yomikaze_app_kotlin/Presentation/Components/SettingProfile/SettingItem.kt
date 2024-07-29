package com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SettingObject(
    val iconStart: Int,
    val iconStartColor: Color? = null,
    val title: String,
    val iconEnd: Int,
    val route: String,
    val isLoginMethod: Boolean = false

)

@Composable
fun SettingItemComponent(
    iconStart: Int,
    iconStartColor: Color? = null,
    title: String,
    iconEnd: Int,
    isLoginMethod: Boolean? = false,
    onClicked: () -> Unit
) {
    var finalIconStartColor = iconStartColor ?: MaterialTheme.colorScheme.surfaceTint
    if (iconStartColor == null) {
        finalIconStartColor = MaterialTheme.colorScheme.surfaceTint
    } else {
        finalIconStartColor = iconStartColor
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .clickable { onClicked() }
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                painter = painterResource(id = iconStart),
                contentDescription = null,
                tint = finalIconStartColor,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = title,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        if (isLoginMethod == true) {
            Image(
                painter = painterResource(id = iconEnd),
                contentDescription = "Logo",
                modifier = Modifier.size(25.dp).offset(x = -(10).dp, y = 0.dp),
            )
        }else {
            IconButton(onClick = { onClicked() }) {
                Icon(
                    painter = painterResource(id = iconEnd),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f),
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }

    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        thickness = 1.dp,
    )

}
