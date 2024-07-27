package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.changeDateTimeFormat
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.processNameByComma
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.IconicDataComicDetail
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.AddToLibraryDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.RatingComicDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.ShareDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu.MenuOptions
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NoNetworkAvailable
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.ImageShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.LineLongShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.LineShortShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.TagShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.TriangleShimmerLoading
import com.example.yomikaze_app_kotlin.R

@Composable
fun ComicDetailsView(
    comicId: Long,
    navController: NavController,
    comicDetailViewModel: ComicDetailViewModel = hiltViewModel()
) {

    val state by comicDetailViewModel.state.collectAsState()


    //set navController for viewModel
    comicDetailViewModel.setNavController(navController)

    if (CheckNetwork()) {
        LaunchedEffect(Unit) {
            comicDetailViewModel.getComicDetailsFromApi(comicId = comicId)
//            comicDetailViewModel.getAllComicCommentByComicId(comicId = comicId)
        }

        LaunchedEffect(
            key1 = state.isRatingComicSuccess,
            key2 = state.isFollowComicSuccess
        ) {
            comicDetailViewModel.getComicDetailsFromApi(comicId = comicId)
        }
        ComicDetailContent(
            comicId = comicId,
            navController = navController,
            comicDetailViewModel = comicDetailViewModel,
            state = state
        )
    } else {

        //   Log.d("ComicDetailsView", "CheckNetwork:")
        comicDetailViewModel.getComicByIdDB(comicId = comicId)
        Log.d("ComicDetailsView", "comic exist: ${state.isComicExistInDB}")
        if (state.isComicExistInDB) {
            ComicDetailContent(
                comicId = comicId,
                navController = navController,
                comicDetailViewModel = comicDetailViewModel,
                state = state
            )
        } else {
            NoNetworkAvailable()
        }
    }


}


/**
 * TODO: Implement ComicDetailContent
 */
@Composable
fun ComicDetailContent(
    comicId: Long,
    navController: NavController,
    comicDetailViewModel: ComicDetailViewModel,
    state: ComicDetailState
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
    val context = LocalContext.current

    var hasShared by remember { mutableStateOf(false) }

    val listTitlesOfComicMenuOption = listOf(
        MenuOptions("Add to Library", "add_to_library_dialog_route", R.drawable.ic_library),
        MenuOptions("Download", "choose_chapter_download_route", R.drawable.ic_download),
        MenuOptions("Rating", "rating_dialog_route", R.drawable.ic_star_fill),
        MenuOptions("Report", "report_dialog_route", R.drawable.ic_report),
        MenuOptions("Share", "share_dialog_route", R.drawable.ic_share),
    )
    val imagePath = if (CheckNetwork()) APIConfig.imageAPIURL.toString() else {
        ""
    }
    var isNetworkAvailable by remember {
        mutableStateOf(true)
    }
    isNetworkAvailable = CheckNetwork()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image with blur
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onSurface)

        ) {
            // banner image
            ///data/user/0/com.example.yomikaze_app_kotlin/files/image_1719475024110.jpg
            if (state.isLoading) {
                ImageShimmerLoading()
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imagePath + state.comicResponse?.banner)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder_430_184),
                    error = painterResource(R.drawable.placeholder_430_184),
                    contentDescription = "Comic Banner Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(194.dp) //bug background
                        .blur(10.dp),
                )
            }
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
                        val previousRoute = navController.previousBackStackEntry?.destination?.route
                        if (previousRoute?.startsWith("home_route") == true
                            || previousRoute?.startsWith("bookcase_route") == true
                            || previousRoute?.startsWith("notification_route") == true
                            || previousRoute?.startsWith("ranking_route") == true
                            || previousRoute?.startsWith("download_detail_route") == true

                        ) {
                            Log.d("ComicDetailsView", "previousRoute1: Hung1")
                            navController.popBackStack()
                        } else {
                            Log.d("ComicDetailsView", "previousRoute2: Hung2")
                            navController.navigate("home_route")
                        }
//                        if(!isNetworkAvailable){
//                            navController.navigate("main_screen_route")
//                        }
                        // check if start as intent

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
                                .background(MaterialTheme.colorScheme.onErrorContainer)

                        ) {
                            listTitlesOfComicMenuOption.forEachIndexed { index, menuOptions ->
                                DropdownMenuItem(
                                    onClick = {
                                        showPopupMenu = false
                                        when (menuOptions.route) {
                                            "add_to_library_dialog_route" -> {
                                                if (!comicDetailViewModel.checkUserIsLogin()) {
                                                    Toast.makeText(
                                                        context,
                                                        "Please sign in to use this feature",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                } else {
                                                    showDialog = 1
                                                }
                                            }

                                            "choose_chapter_download_route" -> {
                                                comicDetailViewModel.navigateToChooseChapterDownload(
                                                    comicId = comicId,
                                                    comicName = state.comicResponse?.name ?: ""
                                                )
                                            }

                                            "rating_dialog_route" -> {
                                                if (!comicDetailViewModel.checkUserIsLogin()) {
                                                    Toast.makeText(
                                                        context,
                                                        "Please sign in to use this feature",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                } else {
                                                    showDialog = 3
                                                }
                                            }

                                            "report_dialog_route" -> {
                                                if (!comicDetailViewModel.checkUserIsLogin()) {
                                                    Toast.makeText(
                                                        context,
                                                        "Please sign in to use this feature",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                } else {
                                                    showDialog = 4
                                                }
                                            }

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
                                            fontSize = 14.sp,
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
                            val context = LocalContext.current

                            when (showDialog) {
                                1 -> AddToLibraryDialog(
                                    comicId = comicId,
                                    comicDetailViewModel = comicDetailViewModel,
                                    isInComic = true,
                                    isFollowed = state.comicResponse?.isFollowing ?: false,
                                    onDismiss = { showDialog = null })

                                3 -> RatingComicDialog(
                                    comicId = comicId,
                                    onDismiss = { showDialog = null },
                                    state = state,
                                    comicDetailViewModel = comicDetailViewModel
                                )

                                4 -> CustomDialog4(onDismiss = { showDialog = null })
                                5 -> {
                                    ShareDialog(
                                        text = "https://yomikaze.org/comic_detail/$comicId",
                                        context = context
                                    )
                                    showDialog = null
                                }
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
                        var image: String
                        if (CheckNetwork()) {
                            image = APIConfig.imageAPIURL.toString() + state.comicResponse?.cover
                        } else {
                            image = state.comicResponse?.cover ?: ""
                        }
                        if (state.isLoading) {
//                            BannerShimmerLoading()
                        } else {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imagePath + state.comicResponse?.cover)
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .build(),
                                placeholder = painterResource(R.drawable.placeholder),
                                error = painterResource(R.drawable.placeholder),
                                contentDescription = "Comic Cover Image",
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
                        }

                        // details
                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 40.dp)
                        ) {
                            if (state.isLoading) {
                                LineShortShimmerLoading()
                            } else {
                                Text(
                                    text = state.comicResponse?.name ?: "Comic Name",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 4,
                                    overflow = TextOverflow.Ellipsis,
                                    lineHeight = 15.sp,
                                    modifier = Modifier.width(250.dp)
                                )
                            }
                            if (state.isLoading) {
                                LineLongShimmerLoading()
                            } else {
                                Text(
                                    text = processNameByComma(
                                        state.comicResponse?.aliases ?: listOf("")
                                    ),
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    lineHeight = 15.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            if (state.isLoading) {
                                LineLongShimmerLoading()
                            } else {


                                Text(
                                    text = "Author: ${
                                        processNameByComma(
                                            state.comicResponse?.authors ?: listOf(
                                                ""
                                            )
                                        )
                                    }",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.height(1.dp))

                            if (state.isLoading) {
                                LineLongShimmerLoading()
                            } else {


                                Text(
                                    text = "Publish Date: ${changeDateTimeFormat(state.comicResponse?.publicationDate ?: "")}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic

                                )
                            }
                            if (state.isLoading) {
                                TagShimmerLoading()
                            } else {
                                TagComponent(status = state.comicResponse?.status ?: "")
                            }

                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(top = 170.dp)
            //.background(MaterialTheme.colorScheme.onSurface)
            .drawWithCache {
                //  val shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
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
                //.offset(y = (20).dp)
                .padding(start = 8.dp, top = 6.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // for icon
            Row(
                horizontalArrangement = Arrangement.spacedBy(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.isLoading) {
                    TriangleShimmerLoading()
                } else {
                    IconicDataComicDetail(
                        icon = R.drawable.ic_eye,
                        iconColor = MaterialTheme.colorScheme.surface,
                        number = state.comicResponse?.views ?: 0,
                        numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        title = "Views",
                        iconWidth = 26,
                        iconHeight = 16,
                    )
                }

                if (state.isLoading) {
                    TriangleShimmerLoading()
                } else {
                    IconicDataComicDetail(
                        icon = R.drawable.ic_following,
                        iconColor = MaterialTheme.colorScheme.surface,
                        number = state.comicResponse?.follows ?: 0,
                        numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        title = "Following",
                        iconWidth = 12,
                        iconHeight = 16,
                    )
                }
                if (state.isLoading) {
                    TriangleShimmerLoading()
                } else {
                    IconicDataComicDetail(
                        icon = R.drawable.ic_star_fill,
                        iconColor = MaterialTheme.colorScheme.surface,
                        numberRating = state.comicResponse?.averageRating,
                        numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        title = "Rating",
                        iconWidth = 20,
                        iconHeight = 20,
                    )
                }

                if (state.isLoading) {
                    TriangleShimmerLoading()
                } else {
                    IconicDataComicDetail(
                        icon = R.drawable.ic_comment,
                        iconColor = MaterialTheme.colorScheme.surface,
                        number = state.comicResponse?.comments ?: 0,
                        numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        title = "Comments",
                        iconWidth = 20,
                        iconHeight = 20,
                    )
                }
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
                        onClick = { tabIndex = index },
                    )
                }
            }
            when (tabIndex) {
                0 -> DescriptionInComicDetailView(
                    state = state,
                    comicDetailViewModel = comicDetailViewModel,
                    comicId = comicId,
                    comicName = state.comicResponse?.name ?: ""
                )

                1 -> ListChapterInComicDetailView(
                    comicDetailViewModel = comicDetailViewModel,
                    comicId = comicId
                    //  listChapter = listChapter
                )
            }
        }
    }
}


// change color for tab layout
@Composable
fun changeColorForTabComicDetail(tabIndex: Int, index: Int): Color {
    return if (tabIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer
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

