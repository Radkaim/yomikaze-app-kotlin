package com.example.yomikaze_app_kotlin.Presentation.Components.TopAppBar


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yomikaze_app_kotlin.R


@Composable
fun DefaultTopAppBar(
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    isProfile: Boolean = false,
    onLogoClicked: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onSettingClicked: () -> Unit = {}

) {
    TopAppBar(
        title = {},
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        navigationIcon = {
            IconButton(onClick = {
                if (isProfile) {
                    onLogoClicked()
                }
            })
            {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
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
            if (isProfile) {
                IconButton(onClick = { onSettingClicked() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_setting),
                        contentDescription = "Setting"
                    )
                }

            } else {
                IconButton(onClick = { onSearchClicked() }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )

                }
            }
        }
    )
}




