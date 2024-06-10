package com.example.yomikaze_app_kotlin.Presentation.Components.CardComic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yomikaze_app_kotlin.R
import com.example.yomikaze_app_kotlin.ui.AppTheme
import com.example.yomikaze_app_kotlin.ui.YomikazeappkotlinTheme

@Composable
fun CardComicColumn(navController1: NavController) {

    val item: List<ComicCardItem> = listOf(
        ComicCardItem(
            comicName = "Naruto",
            image = R.drawable.logo,
            authorName = "23",
            chapter = "123"
        ),
        ComicCardItem(
            comicName = "Naruto",
            image = R.drawable.logo,
            authorName = "23",
            chapter = "123"
        ),
        ComicCardItem(
            comicName = "Naruto",
            image = R.drawable.logo,
            authorName = "23",
            chapter = "123"
        ),
        ComicCardItem(
            comicName = "Naruto",
            image = R.drawable.logo,
            authorName = "23",
            chapter = "123"
        ),
        ComicCardItem(
            comicName = "Naruto",
            image = R.drawable.logo,
            authorName = "23",
            chapter = "123"
        ),
        ComicCardItem(
            comicName = "Naruto",
            image = R.drawable.logo,
            authorName = "23",
            chapter = "123"
        )
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(item) { item ->
            ColumnItem(item = item)
        }
    }
}

@Composable
fun ColumnItem(item: ComicCardItem) {
    Surface(
        modifier = Modifier
            .height(200.dp)
            .width(142.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.tertiary
    ) {
        Column(
            modifier = Modifier
                .height(200.dp)
                .width(142.dp)
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .width(138.dp)
                    .height(162.dp),
                painter = painterResource(id = item.image),
                contentDescription = item.comicName,
                contentScale = ContentScale.Crop
            )
            Text(text = item.comicName, fontWeight = FontWeight.SemiBold)
            Text(text = item.authorName)
        }
    }
}
@Preview
@Composable
fun CardComicColumnPreview() {
    YomikazeappkotlinTheme(appTheme = AppTheme.LIGHT) {
        val navController = rememberNavController()
        CardComicColumn(navController)
    }
}