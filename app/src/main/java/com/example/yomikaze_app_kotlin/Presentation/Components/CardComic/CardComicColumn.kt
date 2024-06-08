package com.example.yomikaze_app_kotlin.Presentation.Components.CardComic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
            title = "Naruto",
            image = R.drawable.logo,
            chapter = "123",
            auth = "123"
        ),
        ComicCardItem(
            title = "Naruto",
            image = R.drawable.logo,
            chapter = "123",
            auth = "123"
        ),
        ComicCardItem(
            title = "Naruto",
            image = R.drawable.logo,
            chapter = "123",
            auth = "123"
        ),
        ComicCardItem(
            title = "Naruto",
            image = R.drawable.logo,
            chapter = "123",
            auth = "123"
        ),
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(item) { item ->
            RowItem(item = item)
        }
    }

}

//}
@Composable
fun ColumnItem(item: ComicCardItem){
    Surface(
        modifier = Modifier
            .height(214.dp)
            .width(127.dp)
            .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
//        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .height(204.dp)
                .width(127.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .height(128.dp)
                    .width(114.dp),
                painter = painterResource(id = item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Crop
            )
//        Spacer(modifier = Modifier.height(8.dp))

            Text(text = item.title, fontWeight = FontWeight.SemiBold)
            Text(text = item.auth)
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