package com.example.yomikaze_app_kotlin.Presentation.Screens.Notification

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.Download.SelectedChapterDownloadItem
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationView() {
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

        val chapterIndexes = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        SelectedChapterDownloadItem(
            chapterIndexes = chapterIndexes,
            onDismiss = {},
            onDownload = {}
        )


    }
}
