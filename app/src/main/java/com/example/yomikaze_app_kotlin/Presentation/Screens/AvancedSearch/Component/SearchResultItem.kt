package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.NormalComicCard
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.AdvancedSearchViewModel

@Composable
fun SearchResultItem(
    advancedSearchViewModel: AdvancedSearchViewModel,
    comic: ComicResponse
) {
    NormalComicCard(
        comicId = comic.comicId,
        image = APIConfig.imageAPIURL.toString() + comic.cover,
        comicName = comic.name,
        status = comic.status,
        authorNames = comic.authors,
        publishedDate = comic.publicationDate,
        ratingScore = comic.averageRating,
        follows = comic.follows,
        views = comic.views,
        comments = comic.comments,
        isSearch = true,
        modifier = Modifier
            .fillMaxWidth()
//            .scale(0.8f)
//            .pointerInput(Unit) {advancedSearchViewModel.onNavigateComicDetail(comic.comicId)  }
            .height(119.dp)
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.small
            ),
        onClicked = {advancedSearchViewModel.onNavigateComicDetail(comic.comicId)}
    )
}
