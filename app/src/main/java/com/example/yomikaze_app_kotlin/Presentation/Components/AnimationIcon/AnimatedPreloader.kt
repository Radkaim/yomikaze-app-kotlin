package com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun LottieAnimationComponent(
    animationFileName: Int,
    loop: Boolean = true,
    autoPlay: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            animationFileName
        )
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = if (loop) LottieConstants.IterateForever else 1,
        restartOnPlay = true,
        isPlaying = autoPlay
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier,
    )
}

