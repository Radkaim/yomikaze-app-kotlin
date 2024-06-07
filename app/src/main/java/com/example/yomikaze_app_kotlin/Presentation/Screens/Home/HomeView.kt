package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Presentation.Components.AutoSlider.Autoslider

@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by homeViewModel.state.collectAsState()
    homeViewModel.setNavController(navController)
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


        Log.d("HomeView", "User is logged in: ${state.isUserLoggedIn}")
        if (state.isUserLoggedIn) {

            Button(onClick = { viewModel.onViewMoreHistoryClicked() }) {
                Text(text = "Click me")
            }

        }


}

@Composable
fun Card(){

}


@Preview
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
    HomeView(navController = navController)
}