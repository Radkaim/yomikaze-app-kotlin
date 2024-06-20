package com.example.yomikaze_app_kotlin.Presentation.Components.SettingProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.R

@Composable
fun SettingProfile(
    login: String,
    editProfile: String,
    changePassword: String,
    darkMode: String,
    coinsShop: String,
    aboutUs: String,
    transactionHistory: String,
) {
    Surface(
        modifier = Modifier
            .height(190.dp) // Adjusted height for better visual balance
            .width(90.dp),
//        shape = RoundedCornerShape(8.dp),
//        color = MaterialTheme.
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
//                        horizontalAlignment = Alignment.CenterStar
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = login,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 1.dp),

//                    leadingIcon = {

                )
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dateofbirth),
                        contentDescription = "Select Date of Birth",
                        tint = MaterialTheme.colorScheme.onTertiary

                    )
//                        }
                }
//                Text(text = "Login Method")
            }
        }
    }
}