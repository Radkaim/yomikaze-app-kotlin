package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Presentation.Components.AutoSlider.AutoSlidingCarousel
import com.example.yomikaze_app_kotlin.R
import com.google.accompanist.pager.ExperimentalPagerApi

@Composable
fun HomeView(homeViewModel: HomeViewModel = hiltViewModel()) {
    val state by homeViewModel.state.collectAsState()

    HomeContent(state, homeViewModel)

}



@Composable
fun HomeContent(
    state: HomeState,
    viewModel: HomeViewModel,

    ) {
    if (state.isLoading) {
        Text(
            text = "Loading...",
            modifier = Modifier.padding(16.dp)
        )
    } else if (state.images.isNotEmpty()) {
        Autoslider(images = state.images)
    } else {
        // Show a placeholder or message when there are no images
        Text("No images available")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.home),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )


    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Autoslider(images: List<String>) {

    Card(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        AutoSlidingCarousel(
            itemsCount = images.size,
            itemContent = { index ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(images[index])
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(200.dp)
                )
            }
        )
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    HomeView()
}