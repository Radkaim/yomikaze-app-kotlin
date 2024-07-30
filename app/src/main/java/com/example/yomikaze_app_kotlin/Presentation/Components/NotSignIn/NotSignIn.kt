package com.example.yomikaze_app_kotlin.Presentation.Components.NotSignIn

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun NotSignIn(
    navController: NavController
) {
    var offsetY by remember { mutableStateOf(120f) }

    LaunchedEffect(Unit) {
        while (true) {
            offsetY = if (offsetY == 100f) 140f else 100f
            delay(1000)
        }
    }

    val animatedOffsetY by animateFloatAsState(
        targetValue = offsetY,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
    )
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.outline,
            MaterialTheme.colorScheme.outlineVariant
        )
    )

    val reGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.outlineVariant,
            MaterialTheme.colorScheme.outline
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
//            .shimmerLoadingAnimation()
    ) {
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
            Text(
                text = "Sign In to See the Magic!",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    // brush = gradientBrush,
                ),
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.9f),

                fontWeight = FontWeight.Medium,
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = animatedOffsetY.dp)
                    .padding(bottom = 350.dp)
                    .clickable { navController.navigate("login_route") }

            )
//
//            LottieAnimationComponent(
//                animationFileName = R.raw.open, // Replace with your animation file name
//                loop = true,
//                autoPlay = true,
//                modifier = Modifier
//                    .width(500.dp)
//                    .height(500.dp)
//                    .scale(1f)
//            )
//
//            OutlinedButton(
//                modifier = Modifier
//                    .height(40.dp)
//                    .offset(y = (-150).dp)
////                    .background(brush = gradientBrush, shape = RoundedCornerShape(12.dp))
//                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp))
//                    .width(200.dp),
//
//                shape = RoundedCornerShape(12.dp),
////                border = BorderStroke(
////                    1.dp,
////                    MaterialTheme.colorScheme.primary.copy(alpha = 0.56f)
////                ),
//                onClick = { navController.navigate("login_route") },
//            )
//            {
//                Text(
//                    text = "Join the fun!",
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
//                    fontWeight = FontWeight.Medium,
//                    style = TextStyle(
//                        fontSize = 20.sp,
//                        // brush = reGradientBrush,
//                    ),
//                )
//            }
//        }


    }

}