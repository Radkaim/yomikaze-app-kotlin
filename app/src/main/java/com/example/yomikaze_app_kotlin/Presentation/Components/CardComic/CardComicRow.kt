//package com.example.yomikaze_app_kotlin.Presentation.Components.CardComic
//
//import android.media.Image
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import coil.compose.rememberImagePainter
//import com.example.yomikaze_app_kotlin.R
//
//
//@Composable
//fun CardComicRow(
//navController: NavController
//
//) {
//    val item: List<CardComicRow> = listOf(
//        CardComicRow(
//            comicName = "Naruto",
//            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
//            comicChapter = "123"
//        ),
//        CardComicItem(
//            comicName = "Naruto",
//            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
//            comicChapter = "123"
//        ),
//        CardComicItem(
//            comicName = "Naruto",
//            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
//
//            comicChapter = "123"
//        ),
//    )
//
//    Surface(
//        modifier = Modifier
//            .height(130.dp)
//            .width(71.dp),
//        shape = RoundedCornerShape(8.dp),
//        color = MaterialTheme.colorScheme.tertiary
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(8.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            Image(
//                modifier = Modifier
//                    .width(69.dp)
//                    .height(81.dp)
//                    .background(color = MaterialTheme.colorScheme.tertiary),
//                painter = rememberImagePainter(data = comic.image),
//                contentDescription = comicName,
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = comicName, fontWeight = FontWeight.SemiBold)
//            Text(text = comicChapter)
//        }
//    }
//}