package com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun IconAndNumbers(
    icon: Int,
    iconColor: Color,
    iconWidth: Int? = null,
    iconHeight: Int? = null,
    numberRating: Float? = null,
    number: Long = 0,
    numberColor: Color,
    numberWeight: FontWeight,
    numberSize: Int,
    onClick: () -> Unit? = {}

) {
    // Assign the icon width and height if they are not null
    val finalIconWith = iconWidth ?: 24
    val finalIconHeight = iconHeight ?: 24

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.padding(top = 10.dp).clickable { onClick() },
    )
    {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .width(finalIconWith.dp)
                .height(finalIconHeight.dp)
        )
        Text(
            text = numberRating?.toString() ?: changeTextFormat(number!!),
            color = numberColor,
            fontSize = numberSize.sp,
            fontWeight = numberWeight
        )
    }
}

fun changeTextFormat(number: Long): String {
    var numberAfterDivide = number
    return when (number) {
        in 1000..999000 -> {
            numberAfterDivide /= 1000
            "$numberAfterDivide" + "K"
        }

        in 1000000..999000000 -> {
            numberAfterDivide /= 1000000
            "$numberAfterDivide" + "M"
        }

        in 1000000000..999000000000 -> {
            numberAfterDivide /= 1000000000
            "$numberAfterDivide" + "B"
        }

        else -> number.toString()
    }
}