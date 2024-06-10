package com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewComment.CommentComicView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewFollow.FollowComicView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewHot.HotComicView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewRating.RatingComicView
import com.example.yomikaze_app_kotlin.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RankingView(initialTab: Int, navController: NavController) {
    var tabIndex by remember { mutableStateOf(initialTab) }

    val tabs = listOf("Hot", "Rating", "Comment", "Follow")

    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "Ranking",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon",
                        )
                    }

                },
            )
        })
    {

        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = MaterialTheme.colorScheme.tertiary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) // Change this to your desired color
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = {
                        Text(
                            text = title,
                            color = changeColor(tabIndex, index),
                            fontWeight = FontWeight.Medium

                        )
                    },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },

                        icon = {
                            setIcon(index, tabIndex)
                        }
                    )
                }
            }
            when (tabIndex) {
                0 -> HotComicView(navController = navController)
                1 -> RatingComicView()
                2 -> CommentComicView()
                3 -> FollowComicView()
            }
        }
    }
}


/**
 * TODO: set icon for each tab
 */
@Composable
fun setIcon(index: Int, tabIndex: Int) {
    return when (index) {
        0 -> {
            Icon(
                painterResource(id = R.drawable.ic_ranking_home),
                contentDescription = "Hot icon",
                tint = changeColor(tabIndex, 0),
                modifier = changeSizeIcon()
            )
        }

        1 -> {
            Icon(
                painterResource(id = R.drawable.ic_star_fill),
                contentDescription = "Rating icon",
                tint = changeColor(tabIndex, 1),
                modifier = changeSizeIcon()
            )
        }

        2 -> {
            Icon(
                painterResource(id = R.drawable.ic_comment),
                contentDescription = "Comment icon",
                tint = changeColor(tabIndex, 2),
                modifier = changeSizeIcon()
            )
        }

        3 -> {
            Icon(
                painterResource(id = R.drawable.ic_following),
                contentDescription = "Follow icon",
                tint = changeColor(tabIndex, 3),
                modifier = changeSizeIcon()
            )
        }

        else -> {}
    }
}

@Composable
fun changeSizeIcon():Modifier {
    return Modifier
        .width(20.dp)
        .height(20.dp)
}

@Composable
fun changeColor(tabIndex: Int, index: Int): Color {
    return if (tabIndex == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary.copy(
        alpha = 0.36f
    )
}

@Preview
@Composable
fun BookcasePreview() {
    RankingView(initialTab = 0, navController = rememberNavController())
}