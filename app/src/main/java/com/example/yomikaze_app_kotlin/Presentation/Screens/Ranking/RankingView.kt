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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewComment.CommentComicView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewFollow.FollowComicView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewHot.HotComicView
import com.example.yomikaze_app_kotlin.Presentation.Screens.Ranking.ViewRating.RatingComicView
import com.example.yomikaze_app_kotlin.R
import com.example.yomikaze_app_kotlin.ui.AppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RankingView(
    index: Int? = 0,
    navController: NavController,
    viewModel: RankingViewModel = hiltViewModel(),
) {
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()

    LaunchedEffect(Unit) {
        if (index != selectedTabIndex && index != null && index != 0) {
            viewModel.setSelectedTabIndex(index)
        }
    }


    val tabs = listOf("Hot", "Rating", "Comment", "Follow")

    Scaffold(
        topBar = {
            CustomAppBar(
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
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.tertiary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) // Change this to your desired color
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = {
                        Text(
                            text = title,
                            color = changeColorForRankingTabScreen(selectedTabIndex, index),
                            fontWeight = FontWeight.Medium

                        )
                    },
                        selected = selectedTabIndex == index,
                        onClick = { viewModel.setSelectedTabIndex(index) },

                        icon = {
                            setIcon(index, selectedTabIndex)
                        }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> HotComicView(navController = navController)
                1 -> RatingComicView(navController = navController)
                2 -> CommentComicView(navController = navController)
                3 -> FollowComicView(navController = navController)
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
                tint = changeColorForRankingTabScreen(tabIndex, 0),
                modifier = changeSizeIcon()
            )
        }

        1 -> {
            Icon(
                painterResource(id = R.drawable.ic_star_fill),
                contentDescription = "Rating icon",
                tint = changeColorForRankingTabScreen(tabIndex, 1),
                modifier = changeSizeIcon()
            )
        }

        2 -> {
            Icon(
                painterResource(id = R.drawable.ic_comment),
                contentDescription = "Comment icon",
                tint = changeColorForRankingTabScreen(tabIndex, 2),
                modifier = changeSizeIcon()
            )
        }

        3 -> {
            Icon(
                painterResource(id = R.drawable.ic_following),
                contentDescription = "Follow icon",
                tint = changeColorForRankingTabScreen(tabIndex, 3),
                modifier = changeSizeIcon()
            )
        }

        else -> {}
    }
}

@Composable
fun changeSizeIcon(): Modifier {
    return Modifier
        .width(20.dp)
        .height(20.dp)
}

@Composable
fun changeColorForRankingTabScreen(tabIndex: Int, index: Int): Color {
    val context = LocalContext.current
    val appPreference = AppPreference(context)
    return if (appPreference.getTheme() == AppTheme.LIGHT || appPreference.getTheme() == AppTheme.DEFAULT) {

        if (tabIndex == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary.copy(
            alpha = 0.36f
        )
    } else {
        if (tabIndex == index) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.background.copy(
            alpha = 0.36f
        )
    }
}
