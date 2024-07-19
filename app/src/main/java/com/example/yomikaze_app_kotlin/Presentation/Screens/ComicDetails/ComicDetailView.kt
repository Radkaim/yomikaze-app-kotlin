package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Domain.Models.Chapter
import com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.ChapterCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.changeDateTimeFormat
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.processNameByComma
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.IconicDataComicDetail
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Comment.CommentCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.CreateCategoryDialog
import com.example.yomikaze_app_kotlin.Presentation.Components.Dialog.UnlockChapterDialogComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu.MenuOptions
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NoNetworkAvailable
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.ComponentRectangleLineLong
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.NormalComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.LibraryViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment.ComicCommentViewModel
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
//    LaunchedEffect(Unit){
//        comicDetailViewModel.getComicDetailsFromApi(comicId = comicId)
//    }


    // comicDetailViewModel.getComicDetailsFromApi(comicId = comicId)
    //comicDetailViewModel.downloadComic(state.comicResponse ?: return)
    if (CheckNetwork()) {
        LaunchedEffect(Unit) {
            Log.d("ComicDetailsView", "launchedEffect:1")
            comicDetailViewModel.getComicDetailsFromApi(comicId = comicId)
            comicDetailViewModel.getAllComicCommentByComicId(comicId = comicId)
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

    var hasShared by remember { mutableStateOf(false) }

    val listTitlesOfComicMenuOption = listOf(
        MenuOptions("Add to Library", "add_to_library_dialog_route", R.drawable.ic_library),
        MenuOptions("Download", "choose_chapter_download_route", R.drawable.ic_download),
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
                .background(MaterialTheme.colorScheme.onSurface)

        ) {
            // banner image
            ///data/user/0/com.example.yomikaze_app_kotlin/files/image_1719475024110.jpg
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(APIConfig.imageAPIURL.toString() + state.comicResponse?.banner)
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
                            navController.navigate("main_screen_route")
                        }
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
                                .background(MaterialTheme.colorScheme.onSurface)

                        ) {
                            listTitlesOfComicMenuOption.forEachIndexed { index, menuOptions ->
                                DropdownMenuItem(
                                    onClick = {
                                        showPopupMenu = false
                                        when (menuOptions.route) {
                                            "add_to_library_dialog_route" -> showDialog = 1
                                            "choose_chapter_download_route" -> {
                                                comicDetailViewModel.navigateToChooseChapterDownload(
                                                    comicId = comicId,
                                                    comicName = state.comicResponse?.name ?: ""
                                                )
                                            }

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
                                    state = state,
                                    onDismiss = { showDialog = null })

                                3 -> RatingComicDialog(
                                    comicId = comicId,
                                    onDismiss = { showDialog = null },
                                    state = state,
                                    comicDetailViewModel = comicDetailViewModel
                                )

                                4 -> CustomDialog4(onDismiss = { showDialog = null })
                                5 -> {
                                    Share(
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
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(APIConfig.imageAPIURL.toString() + state.comicResponse?.cover)
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

                        // details
                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 40.dp)
                        ) {
                            Text(
                                text = state.comicResponse?.name ?: "Comic Name",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 15.sp,
                                modifier = Modifier.width(250.dp)
                            )
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
                            Spacer(modifier = Modifier.height(1.dp))
                            Text(
                                text = "Publish Date: ${changeDateTimeFormat(state.comicResponse?.publicationDate ?: "")}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Italic

                            )
                            TagComponent(status = state.comicResponse?.status ?: "")
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
                IconicDataComicDetail(
                    icon = R.drawable.ic_eye,
                    iconColor = MaterialTheme.colorScheme.surface,
                    number = state.comicResponse?.views ?: 0,
                    numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    title = "Views",
                    iconWidth = 26,
                    iconHeight = 16,
                )
                IconicDataComicDetail(
                    icon = R.drawable.ic_following,
                    iconColor = MaterialTheme.colorScheme.surface,
                    number = state.comicResponse?.follows ?: 0,
                    numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    title = "Following",
                    iconWidth = 12,
                    iconHeight = 16,
                )
                IconicDataComicDetail(
                    icon = R.drawable.ic_star_fill,
                    iconColor = MaterialTheme.colorScheme.surface,
                    numberRating = state.comicResponse?.averageRating ?: 0f,
                    numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    title = "Rating",
                    iconWidth = 20,
                    iconHeight = 20,
                )
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


/**
 * TODO: Description and List Chapter view
 */

@Composable
fun DescriptionInComicDetailView(
    state: ComicDetailState,
    comicDetailViewModel: ComicDetailViewModel,
    comicId: Long,
    comicName: String,
    comicCommentViewModel: ComicCommentViewModel = hiltViewModel()
) {
    var isExpanded by remember { mutableStateOf(false) }
    val description = state.comicResponse?.description
    val comicCommentState by comicCommentViewModel.state.collectAsState()

    LaunchedEffect(key1 = comicCommentState.isDeleteCommentSuccess) {
        comicDetailViewModel.getAllComicCommentByComicId(comicId = comicId)
    }

    //for tag genre
    val listTag = state.listTagGenres ?: emptyList()
//    val ofsety = if (state.listComicComment!!.isNotEmpty()) 0.dp else -70.dp
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
//            .wrapContentHeight()
//            .offset(y = ofsety)
            .padding(top = 10.dp, start = 2.dp, end = 8.dp, bottom = 10.dp),
    ) {
        //TODO: Description
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .clickable { isExpanded = !isExpanded }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val maxLine: Int = if (isExpanded) 25 else 3
                    Text(
                        text = description ?: "Description",
                        maxLines = maxLine,
                          overflow = TextOverflow.Ellipsis,
                    )
                    if (isExpanded) {
                        Text(
                            text = "Less",
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                        )
                    } else {
                        if (description != null) {
                            if (description.length > 70) {
                                Text(
                                    text = "More",
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                    fontSize = 14.sp,
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(start = 2.dp, top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listTag) { tag ->
                    TagComponent(status = tag.name)
                }
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Button(
                    modifier = Modifier
                        .width(250.dp)
                        .height(40.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(20.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.onSecondary,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    onClick = {
                        //TODO
                        //    comicDetailViewModel.downloadComic(state.comicResponse!!)
                    }) {
                    if (state.comicResponse?.isRead ?: false) {
                        Text(
                            text = "Continue Reading",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Log.d(
                            "DescriptionInComicDetailView",
                            "DescriptionInComicDetailView: ${state.comicResponse?.isRead}"
                        )
                        Text(
                            text = "Start Reading",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }
        if(state.listComicComment!!.isNotEmpty()) {
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "New Comments",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            comicDetailViewModel.navigateToComicComment(
                                comicId = comicId,
                                comicName = comicName
                            )
                        }
                    ) {
                        Text(
                            text = "More",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Icon(
                            painterResource(id = R.drawable.ic_next),
                            contentDescription = null,
                            modifier = Modifier
                                .width(10.dp)
                                .height(10.dp),
                        )
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }
        //   for commment
        if (state.isListComicCommentLoading) {
            item {
                repeat(3) {
                    NormalComicCardShimmerLoading()
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
        if (!state.isListComicCommentLoading && state.listComicComment!!.isNotEmpty()) {
            item {
                state.listComicComment.forEach { comment ->
                    CommentCard(
                        comicId = comicId,
                        commentId = comment.id,
                        content = comment.content,
                        authorName = comment.author.name,
                        authorImage = (APIConfig.imageAPIURL.toString() + comment.author.avatar)
                            ?: "",
                        roleName = comment.author.roles?.get(0) ?: "",
                        creationTime = comment.creationTime,
                        isOwnComment = comicCommentViewModel.checkIsOwnComment(comment.author.id),
                        isAdmin = comicCommentViewModel.checkIsAdmin(),
                        onEditClicked = {},
                        onDeleteClicked = {},
                        onClicked = {},
                        comicCommentViewModel = comicCommentViewModel
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp)
                        .clickable {
                            comicDetailViewModel.navigateToComicComment(
                                comicId = comicId,
                                comicName = comicName
                            )
                        }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_comment),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Drop the first comment on this comic!",
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}


@Composable
fun ListChapterInComicDetailView(
    comicDetailViewModel: ComicDetailViewModel,
    comicId: Long
) {

    LaunchedEffect(Unit) {
        comicDetailViewModel.getListChapterByComicId(comicId = comicId)
    }
    var isSelected by remember { mutableStateOf(true) }
    var isReversed by remember { mutableStateOf(false) }

    val listChapter = comicDetailViewModel.state.value.listChapters

    var showDialog by remember { mutableStateOf(false) }
    var selectedChapter by remember { mutableStateOf<Chapter?>(null) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Total Chapter: ${listChapter?.size}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(8.dp)

        )

        SortComponent(
            isOldestSelected = isSelected,
            onOldSortClick = {
                isSelected = true
                isReversed = false
            },
            onnNewSortClick = {
                isSelected = false
                isReversed = true
            }
        )
    }
    //list Chapter
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp)
            .offset(x = (-4).dp),
        verticalArrangement = Arrangement.spacedBy(4.dp) // 8.dp space between each item
    ) {
        listChapter?.let { // means if listChapter is not null
            val sortedList = if (isReversed) it.reversed() else it
            items(sortedList) { chapter ->
                ChapterCard(
                    chapterNumber = chapter.number,
                    title = chapter.name,
                    views = chapter.views,
                    comments = chapter.comments,
                    publishedDate = chapter.creationTime,
                    isLocked = chapter.hasLock,
                    onClick = {
                        if (chapter.hasLock) {
                            selectedChapter = chapter
                            showDialog = true
                        } else {
                            comicDetailViewModel.navigateToViewChapter(
                                comicId,
                                chapter.number
                            )
                        }
                    },
                    onReportClick = {}
                )

            }
        }

    }
    if (showDialog) {
        UnlockChapterDialogComponent(
            title = "Do you want to unlock this chapter?",
            chapterNumber = selectedChapter?.number!!,
            totalCoin = 100,
            coinOfUserAvailable = 200,
            onConfirmClick = {
                //UnlockUC
                //if(state.success) {navigateToViewChapter}
            },
            onDismiss = { showDialog = false }
        )
    }
}


// change color for tab layout
@Composable
fun changeColorForTabComicDetail(tabIndex: Int, index: Int): Color {
    return if (tabIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer
}

@Composable
fun Share(text: String, context: Context) {

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
//        startActivity(context, shareIntent, null)

    context.startActivity(shareIntent)

}

//

/**
 * TODO : dialog for menu option
 */

@Composable
fun AddToLibraryDialog(
    comicId: Long,
    comicDetailViewModel: ComicDetailViewModel,
    state: ComicDetailState,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        comicDetailViewModel.getAllCategory()
    }


    //val categories = state.categoryList.map { it.name }
    val categories = state.categoryList
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedCategories by remember { mutableStateOf(listOf<Long>()) }

    val libraryViewModel = hiltViewModel<LibraryViewModel>()
    val libraryState by libraryViewModel.state.collectAsState()

    LaunchedEffect(key1 = libraryState.isCreateCategorySuccess) {
        Log.d("AddToLibraryDialog", "Create category success")
        comicDetailViewModel.getAllCategory()
    }

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
                .background(Color.Gray.copy(alpha = 0.7f))
                .clickable { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Please select the category you want to save!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "Totals: ${state.totalCategoryResults}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                        // .background(MaterialTheme.colorScheme.onSurface)
                    ) {
                        item {
                            if (!state.isCategoryLoading)
                                repeat(5) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    ComponentRectangleLineLong()
                                }
                        }

                        items(categories) { category ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCategories =
                                            if (selectedCategories.contains(category.id)) {
                                                selectedCategories - category.id
                                            } else {
                                                selectedCategories + category.id
                                            }
                                    }
                                    .padding(8.dp)
                            ) {
                                val colorSelected =
                                    if (selectedCategories.contains(category.id)) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onBackground
                                    }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_add_library),
                                        contentDescription = null,
                                        tint = colorSelected,
                                        modifier = Modifier
                                            .width(17.dp)
                                            .height(17.dp),
                                    )
                                    Text(
                                        text = category.name,
                                        color = colorSelected,
                                        fontSize = 14.sp,
                                    )
                                }
                                val icon = if (selectedCategories.contains(category.id)) {
                                    R.drawable.ic_choose_circle_fill
                                } else {
                                    R.drawable.ic_choose_circle_tick
                                }
                                Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = null,
                                    tint = colorSelected,
                                    modifier = Modifier
                                        .width(17.dp)
                                        .height(17.dp),
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .offset(y = 10.dp)
                                .width(15.dp)
                                .height(15.dp),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        var showPopupMenu by remember { mutableStateOf(false) }
                        Text(
                            text = "Create new personal category",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .clickable { showPopupMenu = true }
                        )
                        when {
                            showPopupMenu -> CreateCategoryDialog(
                                libraryViewModel = libraryViewModel,
                                onDismiss = { showPopupMenu = false }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            //viewModel.addToLibrary(selectedCategories)
                            // Log.d("AddToLibraryDialog", "Selected categories: $selectedCategories")
                            comicDetailViewModel.followComic(comicId, selectedCategories)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f),
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

// Custom CircleCheckbox Composable
@Composable
fun HeartCheckBoxIcon(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val color =
        if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    Canvas(
        modifier = modifier
            .size(24.dp)
            .clickable { onCheckedChange(!checked) }
    ) {
        val color = color
        val path = Path().apply {
            moveTo(size.width / 2, size.height / 5)
            cubicTo(
                size.width / 7, 0f,
                0f, size.height / 2.5f,
                size.width / 2, size.height
            )
            cubicTo(
                size.width, size.height / 2.5f,
                size.width - size.width / 7, 0f,
                size.width / 2, size.height / 5
            )
            close()
        }
        drawPath(
            path = path,
            color = color,
            style = if (checked) Fill else Stroke(width = 2.dp.toPx())
        )
    }
}

@Composable
fun RatingComicDialog(
    comicId: Long,
    state: ComicDetailState,
    comicDetailViewModel: ComicDetailViewModel,
    onDismiss: () -> Unit
) {
    // Remember the state of the stars
    val starState = remember { mutableStateListOf(false, false, false, false, false) }
    val context = LocalContext.current
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
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .width(200.dp)
                    .height(137.dp)
                    .align(Alignment.Center)
                    .offset(y = (-100).dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Rating Comic",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onError,
                            textAlign = TextAlign.Center

                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Display stars
                        starState.forEachIndexed { index, isSelected ->
                            Icon(
                                painter = painterResource(id = if (isSelected) R.drawable.ic_star_fill else R.drawable.ic_star),
                                contentDescription = "Star $index",
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable {
                                        for (i in 0..index) {
                                            starState[i] = true
                                        }
                                        for (i in index + 1 until starState.size) {
                                            starState[i] = false
                                        }
                                    },
                                tint = if (isSelected) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surface
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {

                        Button(
                            modifier = Modifier
                                .width(80.dp)
                                .height(30.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colorScheme.onSecondary,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            onClick = {
                                val selectedStars = starState.count { it }
                                comicDetailViewModel.rateComic(comicId, selectedStars)
                                Log.d("RatingComicDialog", "Selected stars: $selectedStars")
                                if (state.isRatingComicSuccess) {
                                    Toast.makeText(
                                        context,
                                        "Rating successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onDismiss()
                                } else {
                                    onDismiss()
                                }
//
                            }) {
                            Text(
                                text = "Vote",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
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

