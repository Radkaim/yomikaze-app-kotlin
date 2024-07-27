package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.R

@Composable
fun ResetButton(
    onClick: () -> Unit,
    description: String? = ""
) {
    var isShowDescription by remember { mutableStateOf(false) }
    var showDescriptionText by remember { mutableStateOf(description) }

    if (isShowDescription) {
        showDescriptionText = description
    } else {
        showDescriptionText = ""
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        //text hint for each function
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .width(270.dp)
                .wrapContentHeight()
        ) {
            LottieAnimationComponent(
                animationFileName = R.raw.buld, // Replace with your animation file name
                loop = true,
                autoPlay = true,
                modifier = Modifier.size(30.dp)
            )

            Text(
                text = buildAnnotatedString {
                    append(if (isShowDescription) "Hint: " else "Hint")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("$showDescriptionText")
                    }
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                modifier = Modifier
//                    .padding(8.dp)
                    .clickable { isShowDescription = !isShowDescription }
            )

        }

        Button(
            onClick = { onClick() },
            modifier = Modifier
                .wrapContentSize()
                .width(100.dp)
                .height(40.dp)
                .scale(0.7f)
                .offset(x = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.outline,
            )
        ) {
            Text(
                text = "Reset",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }
    }
}




