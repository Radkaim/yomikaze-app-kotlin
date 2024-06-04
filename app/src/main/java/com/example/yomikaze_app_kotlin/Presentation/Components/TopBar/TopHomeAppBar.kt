package com.example.yomikaze_app_kotlin.Presentation.Components.TopAppBar


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yomikaze_app_kotlin.R


@Composable
fun TopHomeAppBar() {
            TopAppBar(
                title = {
                    Text("Yomikaz12e")
                },
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                navigationIcon = {
                    IconButton(onClick = {
                        Log.d("TopHomeAppBar", "Navigation Icon Clicked")
                    })
                    {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { /* Do something */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }

            )
}




