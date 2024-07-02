package com.example.yomikaze_app_kotlin.Presentation.Screens.Notification

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.CategoryCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationView(
    viewModel: NotificationViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Notification",
                navigationIcon = {},
            )
        })

    {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .height(200.dp)
//                .background(MaterialTheme.colorScheme.background),
//            contentAlignment = Alignment.Center
//        )
//        {
////            LottieAnimationComponent(
////                animationFileName = R.raw.no_connection_2, // Replace with your animation file name
////                loop = true,
////                autoPlay = true,
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .scale(0.15f)
////            )
//            Column {
//                NormalChapterDownload(
//                    orderIndex = 1,
//                    chapterIndex = 0,
//                    totalMbs = 12f,
//                    isDownloaded = true,
//                    isInSelectionMode = true ,
//                    isSelected = true,
//                    onClicked = {}
//                )
//                Spacer(modifier = Modifier.height(1.dp))
//                NormalChapterDownload(
//                    orderIndex = 2,
//                    chapterIndex = 1,
//                    totalMbs = 12f,
//                    isDownloaded = true,
//                    isInSelectionMode = true,
//                    isSelected = false,
//                    onClicked = {}
//                )
//            }
//        }

//        val chapterIndexes = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//        SelectedChapterDownloadItem(
//            chapterIndexes = chapterIndexes,
//            onDismiss = {},
//            onDownload = {}
//        )

        CategoryCard(
            image = "https://i.imgur.com/2ZQ2YbN.jpg",
            name = "Default Default Default Default Default asas dsdasd",
            totalComics = 10,
            onClick = {},
            onOptionsClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        CategoryCard(
            image = "https://i.imgur.com/2ZQ2YbN.jpg",
            name = "Default Default Default Default Default asas dsdasd",
            totalComics = 10,
            onClick = {},
            onOptionsClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}
