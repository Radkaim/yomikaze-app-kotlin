package com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Circle shape
@Composable
fun ComponentCircle() {
    Box(
        modifier = Modifier
            .background(color = Color.LightGray, shape = CircleShape)
            .size(100.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

// square shape
@Composable
fun ComponentSquare() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = Color.LightGray)
            .size(100.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

// rectangle shape

@Composable
fun ComponentRectangle() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = Color.LightGray)
            .height(200.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation() // <--- Here.
    )
}
@Composable
fun BasicComicCardShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(170.dp) // Adjusted height for better visual balance
            .width(90.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

// rectangle shape with line long
@Composable
fun ComponentRectangleLineLong() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(30.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation() // <--- Here.
    )
}

// rectangle shape with line medium
@Composable
fun NormalComicCardShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(126.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun HistoryCardShimmerLoading(){
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(170.dp)
            .width(90.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun TagItemShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(20.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun BoxSelectedDownloadShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(50.dp)
            .width(40.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}
@Composable
fun CommentCardShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(150.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation() // <--- Here.
    )
}

//chapter card
@Composable
fun ChapterCardShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(50.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun ImageShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(113.dp)
            .width(78.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}
@Composable
fun BannerShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .fillMaxHeight()
            .fillMaxWidth()
            .shimmerLoadingAnimation() // <--- Here.
    )
}
@Composable
fun CoinShopCardShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .width(350.dp)
            .height(55.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
//triangle shape
fun TriangleShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(20.dp)
            .width(20.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

// rectangle shape with line short
@Composable
fun LineShortShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 30.dp, width = 100.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun LineLongShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 30.dp, width = 100.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun ButtonReadingShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .width(250.dp)
            .height(40.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun TagShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(50.dp))
            .background(color = Color.LightGray)
            .height(20.dp)
            .width(30.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun DescriptionShimmerLoading() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(50.dp))
            .background(color = Color.LightGray)
            .height(70.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation() // <--- Here.
    )
}