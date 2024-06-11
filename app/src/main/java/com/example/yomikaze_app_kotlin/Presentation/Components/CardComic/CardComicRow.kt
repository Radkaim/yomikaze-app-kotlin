package com.example.yomikaze_app_kotlin.Presentation.Components.CardComic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialogDefaults.shape
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
import androidx.compose.foundation.layout.Column as Column


@Composable
fun CardComicRow(navController: NavController) {

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
            )
        )
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(start = 25.dp, end = 23.dp),
//            .background(color = MaterialTheme.colorScheme.tertiary),
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        item.forEach { item ->
            RowItem(item = item)

        }
    }

}

//}
@Composable
fun RowItem(item: ComicCardItem){
    Surface(
        modifier = Modifier
            .height(130.dp)
            .width(71.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.tertiary
    ) {
        Column(
            modifier = Modifier
                .height(130.dp)
                .width(71.dp)
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        )

        {
            Image(
                modifier = Modifier
                    .width(69.dp)
                    .height(81.dp)
                    .background(color = MaterialTheme.colorScheme.tertiary),
                painter = painterResource(id = item.image),
                contentDescription = item.comicName,
                contentScale = ContentScale.Crop
            )
//        Spacer(modifier = Modifier.height(8.dp))

            Text(text = item.comicName, fontWeight = FontWeight.SemiBold)
            Text(text = item.chapter)
        }
        }
}
@Preview
@Composable
fun CardComicPreview() {
    YomikazeappkotlinTheme(appTheme = AppTheme.LIGHT) {
        val navController = rememberNavController()
        CardComicRow(navController)
    }
}