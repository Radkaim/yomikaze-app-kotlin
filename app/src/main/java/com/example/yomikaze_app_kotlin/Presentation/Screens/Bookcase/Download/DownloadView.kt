package com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard.BookcaseComicCard

@Composable
fun DownloadView(
    navController: NavController,
    viewModel: DownloadViewModel = hiltViewModel()
) {
    viewModel.setNavController(navController)

    val comicsListCardModel = listOf(
        DownloadComic(
            comicId = 1,
            cover = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            name = "Hunter X Hunter",
            status = "On Going",
            authors = listOf("Yoshihiro Togashi", "Yoshihiro Togashi1"),
            publicationDate = "1998-03-03",
            totalMbs = 9f,
        ),
        DownloadComic(
            comicId = 1,
            cover = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            name = "Hunter X Hunter12121",
            status = "On Going",
            authors = listOf("Yoshihiro Togashi", "Yoshihiro Togashi1"),
            publicationDate = "1998-03-03",
            totalMbs = 10.5f,
        ),
        DownloadComic(
            comicId = 1,
            cover = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            name = "Hunter X Hunter",
            status = "On Going",
            authors = listOf("Yoshihiro Togashi", "Yoshihiro Togashi1"),
            publicationDate = "1998-03-03",
            totalMbs = 9f,
        ),
        DownloadComic(
            comicId = 1,
            cover = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            name = "Hunter X Hunter12121",
            status = "On Going",
            authors = listOf("Yoshihiro Togashi", "Yoshihiro Togashi1"),
            publicationDate = "1998-03-03",
            totalMbs = 10.5f,
        ),
        DownloadComic(
            comicId = 1,
            cover = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            name = "Hunter X Hunter",
            status = "On Going",
            authors = listOf("Yoshihiro Togashi", "Yoshihiro Togashi1"),
            publicationDate = "1998-03-03",
            totalMbs = 9f,
        ),
        DownloadComic(
            comicId = 1,
            cover = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images",
            name = "Hunter X Hunter12121",
            status = "On Going",
            authors = listOf("Yoshihiro Togashi", "Yoshihiro Togashi1"),
            publicationDate = "1998-03-03",
            totalMbs = 10.5f,
        ),
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp), // 15.dp space between each card
        modifier = Modifier
            .padding(
                top = 20.dp,
                start = 4.dp,
                end = 4.dp,
                //bottom = 80.dp
            ) // Optional padding for the entire list
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize(Alignment.Center)
            .fillMaxSize()
    ) {
        DownloadViewContent(comicsListCardModel, navController, viewModel)
    }

}

data class DownloadComic(
    val comicId: Long,
    val cover: String,
    val name: String,
    val status: String,
    val authors: List<String>,
    val publicationDate: String,
    val totalMbs: Float
)

@Composable
fun DownloadViewContent(
    comicsListCardModel: List<DownloadComic>,
    navController: NavController,
    viewModel: DownloadViewModel = hiltViewModel()
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),// 8.dp space between each item
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        items(comicsListCardModel) { comic ->
            BookcaseComicCard(
                comicId = comic.comicId,
                image = comic.cover,
                comicName = comic.name,
                status = comic.status,
                authorNames = comic.authors,
                isDownloaded = true,
                totalMbs = comic.totalMbs,
                publishedDate = comic.publicationDate,
                backgroundColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(119.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable {
                        viewModel.onComicDownloadedClick(comic.name, comic.comicId)
                    }
            )
        }
    }
}
