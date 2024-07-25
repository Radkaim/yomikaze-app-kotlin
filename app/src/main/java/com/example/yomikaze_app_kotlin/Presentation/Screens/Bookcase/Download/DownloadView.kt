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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.Models.ComicResponse
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.BookcaseComicCard.BookcaseComicCard
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Download.DownloadDetailsView.DownloadDetailViewModel

@Composable
fun DownloadView(
    navController: NavController,
    downloadViewModel: DownloadViewModel = hiltViewModel(),
    downloadDetailViewModel: DownloadDetailViewModel = hiltViewModel()
) {
    downloadViewModel.setNavController(navController)
    val state by downloadViewModel.state.collectAsState()

    val downloadDetailState by downloadDetailViewModel.state.collectAsState()

    LaunchedEffect(key1 = downloadDetailState.isDeleteSuccess) {
        downloadViewModel.getAllComicsDownloadedDB()
    }
    val comicsListCardModel = state.listComicsDB


    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp), // 15.dp space between each card
        modifier = Modifier
            .padding(
//                top = 20.dp,
                start = 4.dp,
                end = 4.dp,
                //bottom = 80.dp
            ) // Optional padding for the entire list
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize(Alignment.Center)
            .fillMaxSize()
    ) {
        DownloadViewContent(comicsListCardModel, navController, downloadViewModel)
    }

}

@Composable
fun DownloadViewContent(
    comics: List<ComicResponse>,
    navController: NavController,
    downloadViewModel: DownloadViewModel
) {
    LaunchedEffect(Unit){
        downloadViewModel.getAllComicsDownloadedDB()
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),// 8.dp space between each item
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp,bottom = 60.dp)
    ) {
        items(comics) { comic ->
            BookcaseComicCard(
                comicId = comic.comicId,
                image = comic.cover,
                comicName = comic.name,
                status = comic.status,
                authorNames = comic.authors,
                isDownloaded = true,
                downloadViewModel = downloadViewModel,
                isDeleted = true,
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
                        downloadViewModel.onComicDownloadedClick(comic.name, comic.comicId)
                    },
                onClicked = {
                    downloadViewModel.onComicDownloadedClick(comic.name, comic.comicId)
                },
//                onDeleteClicked = {
//                    downloadViewModel.delete(comic.comicId, false)
//                }
            )
        }
    }
}
