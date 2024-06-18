package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.ComicStatus
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.IconicDataComicDetail
import com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu.MenuOptions
import com.example.yomikaze_app_kotlin.R

@Composable
fun ComicDetailsView(
    comicId: Int,
    navController: NavController,
    comicDetailViewModel: ComicDetailViewModel = hiltViewModel()
) {
    /**
     * for menu option
     */

    var showPopupMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf<Int?>(null) }
    val backgroundColor = MaterialTheme.colorScheme.background

    // for tab layout description and list chapter
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Description", "Chapter")


    //set navController for viewModel
    comicDetailViewModel.setNavController(navController)


    val listTitlesOfComicMenuOption = listOf(
        MenuOptions("Add to Library", "add_to_library_dialog_route", R.drawable.ic_library),
        MenuOptions("Download", "download_dialog_route", R.drawable.ic_download),
        MenuOptions("Rating", "rating_dialog_route", R.drawable.ic_star_fill),
        MenuOptions("Report", "report_dialog_route", R.drawable.ic_report),
        MenuOptions("Share", "share_dialog_route", R.drawable.ic_share),
    )


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image with blur
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // banner image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fblackandyellowotakugamers.com%2Fwp-content%2Fuploads%2F2017%2F06%2Fhunterxhunter-banner.jpeg&f=1&nofb=1&ipt=85a60baa54649349c7996f33d41149db477897d3f60ed278433f1fa527c81f47&ipo=images")
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_430_184),
                contentDescription = "Comic Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(184.dp)
                    .blur(5.dp)
            )
        }

        // Content including back button and menu
        Box() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(300.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back button
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon",
                        )
                    }
                    // Menu
                    Box() {
                        IconButton(onClick = { showPopupMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More")
                        }

                        DropdownMenu(
                            expanded = showPopupMenu,
                            onDismissRequest = { showPopupMenu = false },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.onSurface)

                        ) {
                            listTitlesOfComicMenuOption.forEachIndexed { index, menuOptions ->
                                DropdownMenuItem(
                                    onClick = {
                                        showPopupMenu = false
                                        when (menuOptions.route) {
                                            "add_to_library_dialog_route" -> showDialog = 1
                                            "download_dialog_route" -> showDialog = 2
                                            "rating_dialog_route" -> showDialog = 3
                                            "report_dialog_route" -> showDialog = 4
                                            "share_dialog_route" -> showDialog = 5
                                        }
                                    }) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        modifier = Modifier
                                            .height(15.dp)
                                            .width(125.dp)
                                            .align(Alignment.CenterVertically)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = menuOptions.icon),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSecondary.copy(
                                                alpha = 0.8f
                                            ),
                                            modifier = Modifier
                                                .width(17.dp)
                                                .height(17.dp),
                                        )
                                        Text(
                                            text = menuOptions.title,
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                                if (index < listTitlesOfComicMenuOption.size - 1) {
                                    Divider(
                                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                        thickness = 1.dp
                                    )
                                }
                            }
                        }
                        if (showDialog != null) {
                            when (showDialog) {
                                1 -> CustomDialog3(onDismiss = { showDialog = null })
                                2 -> CustomDialog4(onDismiss = { showDialog = null })
                                3 -> CustomDialog3(onDismiss = { showDialog = null })
                                4 -> CustomDialog4(onDismiss = { showDialog = null })
                                5 -> CustomDialog4(onDismiss = { showDialog = null })
                            }
                        }
                    }
                }


                // Image and Comic details
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp)
                        .offset(y = (-15).dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp)
                        //.offset(y = (-5).dp)
                    ) {
                        // main image
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.6HUddKnrAhVipChl6084pwHaLH%26pid%3DApi&f=1&ipt=303f06472dd41f68d97f5684dc0d909190ecc880e7648ec47be6ca6009cbb2d1&ipo=images")
                                .memoryCachePolicy(CachePolicy.ENABLED)
                                .build(),
                            placeholder = painterResource(R.drawable.placeholder),
                            contentDescription = "Comic Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(78.dp)
                                .height(113.dp)
                                .offset(y = (-20).dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                    shape = MaterialTheme.shapes.small
                                )
                                .shadow(
                                    elevation = 4.dp,
                                    shape = MaterialTheme.shapes.small
                                )
                        )

                        // details
                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 40.dp)
                        ) {
                            Text(
                                text = "Hunter x Hunter",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 2
                            )
                            Text(
                                text = "Hunter",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = "Author: Yoshihiro Togashi",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.height(1.dp))
                            Text(
                                text = "Publish Date: 25/05/2023",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                            ComicStatus(status = "On Going")
                        }
                    }
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .padding(top = 150.dp)
            .drawWithCache {
                val shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                val path = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(0f, 0f, size.width, size.height),
                            topLeft = CornerRadius(30.dp.toPx(), 30.dp.toPx()),
                            topRight = CornerRadius(30.dp.toPx(), 30.dp.toPx())
                        )
                    )
                }
                onDrawBehind {
                    drawPath(path, backgroundColor)
                }
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 20.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // for icon
            Row(
                horizontalArrangement = Arrangement.spacedBy(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconicDataComicDetail(
                    icon = R.drawable.ic_eye,
                    iconColor = MaterialTheme.colorScheme.surface,
                    number = 900000,
                    numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    title = "Views",
                    iconWidth = 26,
                    iconHeight = 16,
                )
                IconicDataComicDetail(
                    icon = R.drawable.ic_following,
                    iconColor = MaterialTheme.colorScheme.surface,
                    number = 90,
                    numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    title = "Following",
                    iconWidth = 12,
                    iconHeight = 16,
                )
                IconicDataComicDetail(
                    icon = R.drawable.ic_star_fill,
                    iconColor = MaterialTheme.colorScheme.surface,
                    numberRating = 4.9f,
                    numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    title = "Rating",
                    iconWidth = 20,
                    iconHeight = 20,
                )
                IconicDataComicDetail(
                    icon = R.drawable.ic_comment,
                    iconColor = MaterialTheme.colorScheme.surface,
                    number = 1000000,
                    numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    title = "Comments",
                    iconWidth = 20,
                    iconHeight = 20,
                )
            }

            Divider(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.36f),
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .offset(x = (-3).dp)
            )


            //Tab Layout for Description and Chapter
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = MaterialTheme.colorScheme.background,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]), // Thiết lập cuộn dọc,
                        color = MaterialTheme.colorScheme.primary // Change this to your desired color
                    )
                },
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                color = changeColorForTabComicDetail(tabIndex, index),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            when (tabIndex) {
                0 -> DescriptionInComicDetailView()
                1 -> ListChapterInComicDetailView(comicDetailViewModel = comicDetailViewModel)
            }
        }
    }
}


/**
 * TODO: Description and List Chapter view
 */

@Composable
fun DescriptionInComicDetailView() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Hunter x Hunter is a Japanese manga series written and" +
                    " illustrated by Yoshihiro Togashi. It has been serialized in Weekly" +
                    " Shōnen Jump magazine since March 3, 1998, although the manga has " +
                    "frequently gone on extended hiatuses since 2006. As of October 2018," +
                    " 380 chapters have been collected into 36 volumes by Shueisha. The story focuses"
        )
    }

}

@Composable
fun ListChapterInComicDetailView(comicDetailViewModel: ComicDetailViewModel) {
    val items = 50
    Row {
        Text(text = "Total Chapter: $items")

        Box() {
            Row(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5.dp))
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(text = "Oldest")

                Text(text = "Newest")
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(45.dp), // 15.dp space between each card
        modifier = Modifier
            .padding(
                top = 15.dp,
                start = 4.dp,
                end = 4.dp,
                bottom = 4.dp
            ) // Optional padding for the entire list
            .background(MaterialTheme.colorScheme.background)
        //.wrapContentSize(Alignment.Center)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp) // 8.dp space between each item
        ) {
            items(items) {
                Text(
                    text = "Chapter $it",
                    modifier = Modifier.clickable { comicDetailViewModel.navigateToViewChapter(it) })
            }
        }
    }

}


// change color for tab layout
@Composable
fun changeColorForTabComicDetail(tabIndex: Int, index: Int): Color {
    return if (tabIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer
}

/**
 * TODO : dialog for menu option
 */

@Composable
fun CustomDialog3(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.7f)) // Màu xám với độ mờ
                .offset(y = (100).dp)
                .clickable { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Dialog 1")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onDismiss) {
                        Text("OK")
                    }
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            }
        }
    }


}

@Composable
fun CustomDialog4(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Dialog 2")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) {
                    Text(Int.MAX_VALUE.toString())
                }
            }
        }
    }
}

