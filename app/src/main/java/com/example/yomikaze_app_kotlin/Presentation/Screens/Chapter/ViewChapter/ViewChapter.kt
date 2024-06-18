package com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.ViewChapter

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.FlipPage.FlipPager
import com.example.yomikaze_app_kotlin.Presentation.Screens.Chapter.FlipPage.FlipPagerOrientation
import com.example.yomikaze_app_kotlin.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewChapter(
    navController: NavController,
    chapterId: Int
) {
    var orientation: FlipPagerOrientation by remember {
        mutableStateOf(FlipPagerOrientation.Vertical)
    }

    val images = listOf(
        "https://i.pinimg.com/236x/48/1e/16/481e16a175a0b4d60f2e3334d35a7fc3.jpg",
        "https://i.pinimg.com/236x/d9/da/6d/d9da6d8a04106e368d0a970c82d26dd8.jpg",
        "https://i.pinimg.com/236x/29/62/17/296217c2f110d13b0f8527079c6917c9.jpg",
        "https://i.pinimg.com/236x/0e/8a/9d/0e8a9da27fc5a051071d29c31ebb191d.jpg",
    )


    val state = rememberPagerState { images.size }
    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "View Chapter",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()

                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                },
            )
        })
    {
        FlipPager(
            state = state,
            modifier = Modifier.fillMaxWidth(),
            orientation = orientation,
        ) { page ->
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            //Comic Image
            images[page].let { image ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = "Comic Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.small
                        )
                        .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
                )
            }
        }
    }

}
