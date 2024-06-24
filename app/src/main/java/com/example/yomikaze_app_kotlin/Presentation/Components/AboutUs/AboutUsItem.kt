package com.example.yomikaze_app_kotlin.Presentation.Components.AboutUs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AboutUsObject(
    val title: String,
    val titleEnd: String? = null,
    val iconEnd: Int? = null,
)

@Composable
fun AboutUsItem(
    aboutUsObjectIcon: AboutUsObject,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = aboutUsObjectIcon.title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        aboutUsObjectIcon.titleEnd?.let {
            Text(
                text = it,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraLight
                )
            )
        }

        aboutUsObjectIcon.iconEnd?.let {
            IconButton(
                modifier = Modifier.size(10.dp),
                onClick = { /* Handle icon click */ }) {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
        thickness = 1.dp,
//        modifier = Modifier.padding(vertical = 8.dp)
    )
}