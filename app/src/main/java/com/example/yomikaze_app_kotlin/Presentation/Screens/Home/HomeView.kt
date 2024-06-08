package com.example.yomikaze_app_kotlin.Presentation.Screens.Home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicColumn
import com.example.yomikaze_app_kotlin.Presentation.Components.CardComic.CardComicRow

@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by homeViewModel.state.collectAsState()
    homeViewModel.setNavController(navController)
    HomeContent(state, homeViewModel, navController)

}


@Composable
fun HomeContent(
    state: HomeState,
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    Column {
        if (state.isLoading) {
            Text(
                text = "Loading...",
                modifier = Modifier.padding(9.dp)
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

        CardComicRow(navController)
        CardComicColumn(navController)
    }

}


@Preview
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
    HomeView(navController = navController)
}