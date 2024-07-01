package com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.yomikaze_app_kotlin.Domain.Models.ComicChapter
import com.example.yomikaze_app_kotlin.Presentation.Components.Chapter.ChapterCard
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.changeDateTimeFormat
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.processNameByComma
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.IconicDataComicDetail
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu.MenuOptions
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
    comicDetailViewModel.getComicDetailsFromApi(comicId = comicId)
    //comicDetailViewModel.downloadComic(state.comicResponse ?: return)

    ComicDetailContent(
        comicId = comicId,
        navController = navController,
        comicDetailViewModel = comicDetailViewModel,
        state = state
    )

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

    val listTitlesOfComicMenuOption = listOf(
        MenuOptions("Add to Library", "add_to_library_dialog_route", R.drawable.ic_library),
        MenuOptions("Download", "download_dialog_route", R.drawable.ic_download),
        MenuOptions("Rating", "rating_dialog_route", R.drawable.ic_star_fill),
        MenuOptions("Report", "report_dialog_route", R.drawable.ic_report),
        MenuOptions("Share", "share_dialog_route", R.drawable.ic_share),
    )

    // test case list chapter
    val listChapter = listOf(
        ComicChapter(
            chapterIndex = 0,
            title = "Superhero Origins 12Origins Origins12 OriginsOri gins12Ori gins",
            views = 100000000,
            comments = 100000000,
            publishedDate = "27/01/2024",
            isLocked = true
        ),
        ComicChapter(
            chapterIndex = 1,
            title = "Superhero Origins12",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = false
        ),
        ComicChapter(
            chapterIndex = 2,
            title = "Superhero Superhero Origins 12Origins Origins12 OriginsOri gins12Ori gins",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = true
        ),
        ComicChapter(
            chapterIndex = 3,
            title = "Superhero Origins14",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = false
        ),
        ComicChapter(
            chapterIndex = 4,
            title = "Superhero Origins15",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = false
        ),
        ComicChapter(
            chapterIndex = 5,
            title = "Superhero Origins16",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = true
        ),
        ComicChapter(
            chapterIndex = 6,
            title = "Superhero Superhero Origins 12Origins Origins12 OriginsOri gins12Ori gins",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = false
        ),
        ComicChapter(
            chapterIndex = 7,
            title = "Superhero Origins18",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = true
        ),
        ComicChapter(
            chapterIndex = 8,
            title = "Superhero Origins19",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = false
        ),
        ComicChapter(
            chapterIndex = 9,
            title = "Superhero Origins20",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = true
        ),
        ComicChapter(
            chapterIndex = 10,
            title = "Superhero Origins21",
            views = 1000,
            comments = 10,
            publishedDate = "27/01/2024",
            isLocked = false
        ),
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
            ///data/user/0/com.example.yomikaze_app_kotlin/files/image_1719475024110.jpg
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(APIConfig.imageAPIURL.toString() + state.comicResponse?.banner)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_430_184),
                error = painterResource(R.drawable.placeholder_430_184),
                contentDescription = "Comic Banner Image",
                contentScale = ContentScale.Fit,
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
                            when (showDialog) {
                                1 -> AddToLibraryDialog(onDismiss = { showDialog = null })
                                2 -> CustomDialog4(onDismiss = { showDialog = null })
                                3 -> RatingComicDialog(
                                    comicId = comicId,
                                    onDismiss = { showDialog = null },
                                    state = state,
                                    comicDetailViewModel = comicDetailViewModel
                                )

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
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 15.sp,
                                modifier = Modifier.width(200.dp)
                            )
                            Text(
                                text = processNameByComma(
                                    state.comicResponse?.aliases ?: listOf("")
                                ),
                                fontSize = 11.sp,
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
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.height(1.dp))
                            Text(
                                text = "Publish Date: ${changeDateTimeFormat(state.comicResponse?.publicationDate ?: "")}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
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
            .padding(top = 160.dp)
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
                0 -> DescriptionInComicDetailView(state = state)
                1 -> ListChapterInComicDetailView(
                    comicDetailViewModel = comicDetailViewModel,
                    listChapter = listChapter
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
    state: ComicDetailState
) {
    val textLength = state.comicResponse?.description?.length ?: 50
    var isExpanded by remember { mutableStateOf(false) }
    val text = state.comicResponse?.description

    val shortText = if (isExpanded) text else text?.take(textLength)

    //for tag genre
    val listTag = state.listTagGenres ?: emptyList()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //  .padding(8.dp)
                    .clickable { isExpanded = !isExpanded }
            ) {
                (if (isExpanded) text else shortText)?.let {
                    Text(
                        text = it,
                    )
                }
                if (shortText != null) {
                    if (!isExpanded && shortText.length <= textLength)
                        Text(
                            text = "...more",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.offset(x = 100.dp, y = 95.dp)
                        )
                    else if (text != null) {
                        if (isExpanded && text.length > 50) {
                            Text(
                                text = "less",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(top = 10.dp)
                            )
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
                    }) {
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
}


@Composable
fun ListChapterInComicDetailView(
    comicDetailViewModel: ComicDetailViewModel,
    listChapter: List<ComicChapter>
) {
    var isSelected by remember { mutableStateOf(true) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Total Chapter: ${listChapter.size}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(8.dp)

        )

        SortComponent(
            isOldestSelected = isSelected,
            onnNewSortClick = { isSelected = false },
            onOldSortClick = { isSelected = true }
        )
    }

    //list Chapter
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = (-4).dp),
        verticalArrangement = Arrangement.spacedBy(1.5.dp) // 8.dp space between each item
    ) {
        items(listChapter) { chapter ->
            ChapterCard(
                chapterIndex = chapter.chapterIndex,
                title = chapter.title,
                views = chapter.views,
                comments = chapter.comments,
                publishedDate = chapter.publishedDate,
                isLocked = chapter.isLocked,
                onClick = { comicDetailViewModel.navigateToViewChapter(chapter.chapterIndex) }) {

            }
        }
    }
}


// change color for tab layout
@Composable
fun changeColorForTabComicDetail(tabIndex: Int, index: Int): Color {
    return if (tabIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer
}

//

/**
 * TODO : dialog for menu option
 */

@Composable
fun AddToLibraryDialog(onDismiss: () -> Unit) {
    val categories = listOf(
        "Category 1",
        "Category 2",
        "Category 3",
        "Category 4",
        "Category 5",
        "Category 6",
        "Category 7"
    )
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedCategories by remember { mutableStateOf(listOf<String>()) }


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
                    Text(text = "Select Categories", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn {
                        items(categories) { category ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCategories =
                                            if (selectedCategories.contains(category)) {
                                                selectedCategories - category
                                            } else {
                                                selectedCategories + category
                                            }
                                    }
                                    .padding(8.dp)
                            ) {
                                CircleCheckbox(
                                    checked = selectedCategories.contains(category),
                                    onCheckedChange = { checked ->
                                        selectedCategories = if (checked) {
                                            selectedCategories + category
                                        } else {
                                            selectedCategories - category
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = category)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            //viewModel.addToLibrary(selectedCategories)
                            Log.d("AddToLibraryDialog", "Selected categories: $selectedCategories")
                            onDismiss()
                        }
                    ) {
                        Text("Add to Library")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

// Custom CircleCheckbox Composable
@Composable
fun CircleCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .size(24.dp)
            .clickable { onCheckedChange(!checked) }
    ) {
        val color = if (checked) Color.Red else Color.Gray
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
    Log.d("RatingComicDialog", "RatingComicDialog: $comicId")
    // Remember the state of the stars
    val starState = remember { mutableStateListOf(false, false, false, false, false) }
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
                                if(state.isRatingComicSuccess){
                                        onDismiss()
                                }
                                else{
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

