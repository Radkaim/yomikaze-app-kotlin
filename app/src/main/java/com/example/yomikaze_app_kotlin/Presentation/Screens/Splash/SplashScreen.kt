package com.example.yomikaze_app_kotlin.Presentation.Screens.Splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.R
import kotlinx.coroutines.withContext


@Composable

fun SplashScreen(navController: NavController) {

//    val scale = remember {
//        androidx.compose.animation.core.Animatable(0f)
//    }
//    val systemUiController = rememberSystemUiController()

    // AnimationEffect
//    val color = MaterialTheme.colorScheme.background
    LaunchedEffect(key1 = true) {
        withContext(kotlinx.coroutines.Dispatchers.Main) {

//        scale.animateTo(
//            targetValue = 0.7f,
//            animationSpec = tween(
//                durationMillis = 200,
//                easing = {
//                    OvershootInterpolator(0.1f).getInterpolation(it)
//                })
//        )
//        delay(100L)
            // Optionally, show the system bars again after the splash screen

            navController.navigate("home_route")
        }

    }
    val brush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.outline,
            MaterialTheme.colorScheme.outlineVariant
        )
    )
    // Image

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = -(50).dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(150.dp)
                .width(150.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Yomikaze",
            fontSize = 30.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                brush = brush,
            ),
            modifier = Modifier.padding(
                top = 220.dp
            )
        )
    }


}

//@Preview
//@Composable
//fun previewSlashScreen(){
//    val navController = rememberNavController()
//    SplashScreen(navController)
//}