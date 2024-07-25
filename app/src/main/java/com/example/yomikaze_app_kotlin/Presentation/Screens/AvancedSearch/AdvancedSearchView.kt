package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NoNetworkAvailable
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.NormalComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.TagItemShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.QueryByComicNameTextField
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.SearchResultItem
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.SliderAdvancedExample
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.TagList


@Composable
fun AdvancedSearchView(
    navController: NavController,
    comicNameSearchText: String?,
    advancedSearchViewModel: AdvancedSearchViewModel = hiltViewModel(),
) {
    val state by advancedSearchViewModel.state.collectAsState()

    if (CheckNetwork()) {
        AdvancedSearchContent(
            navController = navController,
            comicNameSearchText = comicNameSearchText!!,
            advancedSearchViewModel = advancedSearchViewModel,
            state = state
        )
    } else {
        NoNetworkAvailable()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdvancedSearchContent(
    navController: NavController,
    comicNameSearchText: String,
    advancedSearchViewModel: AdvancedSearchViewModel,
    state: AdvancedSearchState
) {
    //work
    LaunchedEffect(Unit) {
        advancedSearchViewModel.updateQueryByComicName(comicNameSearchText)
//
    }

    LaunchedEffect(Unit) {
        advancedSearchViewModel.performAdvancedSearch()
    }

    LaunchedEffect(Unit) {
        advancedSearchViewModel.getTags()
    }
//   advancedSearchViewModel.performAdvancedSearch()


    // item to show
    var itemQueryComicName by remember { mutableStateOf(true) }
    var itemQueryAuthor by remember { mutableStateOf(false) }
    var itemQueryPublisher by remember { mutableStateOf(false) }
    var itemQueryStatus by remember { mutableStateOf(false) }
    var itemQueryFromToDate by remember { mutableStateOf(false) }
    var itemFromToTotalChapter by remember { mutableStateOf(false) }
    var itemFromToTotalView by remember { mutableStateOf(false) }
    var itemFromToAverageRating by remember { mutableStateOf(false) }
    var itemFromToTotalFollow by remember { mutableStateOf(false) }
    var itemQueryTag by remember { mutableStateOf(false) }
    var itemExcludeIncludeMode by remember { mutableStateOf(false) }
    var itemQueryOrderBy by remember { mutableStateOf(false) }

    var resetSlider by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = resetSlider)
    {
        if (resetSlider) {
//            advancedSearchViewModel.resetAverageRatingValue()
            resetSlider = false
        }

    }
    //reset average rating

    //page 1 size 1000
    fun hideAll() {
        itemQueryComicName = false
        itemQueryAuthor = false
        itemQueryPublisher = false
        itemQueryStatus = false
        itemQueryFromToDate = false
        itemFromToTotalChapter = false
        itemFromToTotalView = false
        itemFromToAverageRating = false
        itemFromToTotalFollow = false
        itemQueryTag = false
        itemExcludeIncludeMode = false
        itemQueryOrderBy = false
    }

    //check color for hide all button return true if all item is hide
//check color for hide all button return true if all items are hidden
    val hideAllColorCheck by remember {
        derivedStateOf {
            !itemQueryComicName &&
                    !itemQueryAuthor &&
                    !itemQueryPublisher &&
                    !itemQueryStatus &&
                    !itemQueryFromToDate &&
                    !itemFromToTotalChapter &&
                    !itemFromToTotalView &&
                    !itemFromToAverageRating &&
                    !itemFromToTotalFollow &&
                    !itemQueryTag &&
                    !itemExcludeIncludeMode &&
                    !itemQueryOrderBy
        }
    }


    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Avanced Search",
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
        },
        bottomBar = {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                Divider(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    //TODO: Reset all value
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.outline)
                            .height(40.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .clickable {
                                advancedSearchViewModel.resetState()
                                resetSlider = true
                            },
                        contentAlignment = Alignment.Center

                    ) {
                        Text(
                            text = "Reset All",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )

                    }

                    Divider(
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                        modifier = Modifier
                            .height(40.dp) // Height of the divider to match the height of the Boxes
                            .width(1.dp)   // Width of the divider

                    )

                    //TODO: Hide all item
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .width(100.dp)
                            .background(if (hideAllColorCheck) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.onPrimary)
                            .clickable {
                                hideAll()
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "Hide All",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                    }

                    Divider(
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                        modifier = Modifier
                            .height(40.dp) // Height of the divider to match the height of the Boxes
                            .width(1.dp)   // Width of the divider
                    )

                    //TODO: Search button
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .width(100.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {
                                advancedSearchViewModel.performAdvancedSearch()
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "Search",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )

                    }

                }
            }
        }
    ) {
//        Surface(
//            modifier = Modifier
//                .padding(4.dp)
//                .background(MaterialTheme.colorScheme.background)
//        ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp), // 15.dp space between each card
            modifier = Modifier
                .padding(

                    start = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                ) // Optional padding for the entire list
                .background(MaterialTheme.colorScheme.background)
                .wrapContentSize(Alignment.Center)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp), // 8.dp space between each item
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = 80.dp)

            ) {

                //TODO: search by comic name
                item {
                    showTitle(
                        title = "Comic Name",
                        onShowClick = { itemQueryComicName = it },
                        itemShowBoolean = itemQueryComicName
                    )
                    if (itemQueryComicName) {
                        QueryByComicNameTextField(
                            queryByComicName = state.queryByComicName,
                            onValueChange = { advancedSearchViewModel.updateQueryByComicName(it) },
                            onCLoseClicked = { advancedSearchViewModel.updateQueryByComicName("") }
                        )
                    }
                }

                //Todo: search by author
                item {
                    showTitle(
                        title = "Author",
                        onShowClick = { itemQueryAuthor = it },
                        itemShowBoolean = itemQueryAuthor
                    )
                    if (itemQueryAuthor) {

                    }
                }

                //Todo: search by publisher
                item {
                    showTitle(
                        title = "Publisher",
                        onShowClick = { itemQueryPublisher = it },
                        itemShowBoolean = itemQueryPublisher
                    )
                    if (itemQueryPublisher) {

                    }
                }

                //Todo: search by status
                item {
                    showTitle(
                        title = "Status",
                        onShowClick = { itemQueryStatus = it },
                        itemShowBoolean = itemQueryStatus
                    )
                    if (itemQueryStatus) {

                    }
                }

                //Todo: search by From - To Date
                item {
                    showTitle(
                        title = "From - To Date",
                        onShowClick = { itemQueryFromToDate = it },
                        itemShowBoolean = itemQueryFromToDate
                    )
                    if (itemQueryFromToDate) {
                    }
                }

                //Todo: search by From - To Total Chapter
                item {
                    showTitle(
                        title = "From - To Total Chapter",
                        onShowClick = { itemFromToTotalChapter = it },
                        itemShowBoolean = itemFromToTotalChapter
                    )
                    if (itemFromToTotalChapter) {
                    }
                }

                //Todo: search by From - To Total View
                item {
                    showTitle(
                        title = "From - To Total View",
                        onShowClick = { itemFromToTotalView = it },
                        itemShowBoolean = itemFromToTotalView
                    )
                    if (itemFromToTotalView) {
                    }
                }

                //Todo: search by From - To Average Rating
                item {
                    showTitle(
                        title = "From - To Average Rating",
                        onShowClick = { itemFromToAverageRating = it },
                        itemShowBoolean = itemFromToAverageRating
                    )
                    if (itemFromToAverageRating) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    advancedSearchViewModel.resetAverageRatingValue()
                                    resetSlider = true
                                },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                            ) {
                                Text(
                                    text = "Reset All Tags",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {


                            SliderAdvancedExample(
                                fromValueChange = state.queryFromAverageRating ?: 0f,
                                toValueChange = state.queryToAverageRating ?: 5f,
                                defaultFromValue = 0f,
                                defaultToValue = 5f,
                                resetSlider = resetSlider,
                                onValueFromChange = {
                                    advancedSearchViewModel.updateQueryFromAverageRating(
                                        it
                                    )
                                },
                                onValueToChange = {
                                    advancedSearchViewModel.updateQueryToAverageRating(
                                        it
                                    )
                                }
                            )
                        }
                    }
                }

                //Todo: search by From - To Follow
                item {
                    showTitle(
                        title = "From - To Total Follow",
                        onShowClick = { itemFromToTotalFollow = it },
                        itemShowBoolean = itemFromToTotalFollow
                    )
                    if (itemFromToTotalFollow) {
                    }
                }

                //Todo: search by tag
                item {
                    showTitle(
                        title = "Tag",
                        onShowClick = { itemQueryTag = it },
                        itemShowBoolean = itemQueryTag
                    )
                    if (itemQueryTag) {
                        val tagId = 1L..20L
                        if (state.isTagsLoading) {
                            repeat(10) {
                                TagItemShimmerLoading()
                            }
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = { advancedSearchViewModel.resetTags() },
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .width(100.dp)
                                        .height(40.dp)
                                        .scale(0.7f)
                                        .offset(x = 20.dp),
                                ) {
                                    Text(
                                        text = "Reset All Tags",
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                    )
                                }
                            }
                            TagList(
                                viewModel = advancedSearchViewModel,
                                tags = state.tags
                            )
                        }
                    }
                }

                //Todo: search by Exclude/Include Mode
                item {
                    showTitle(
                        title = "Exclude/Include Mode",
                        onShowClick = { itemExcludeIncludeMode = it },
                        itemShowBoolean = itemExcludeIncludeMode
                    )
                    if (itemExcludeIncludeMode) {
                    }
                }

                //Todo: search by order by
                item {
                    showTitle(
                        title = "Order By",
                        onShowClick = { itemQueryOrderBy = it },
                        itemShowBoolean = itemQueryOrderBy
                    )
                    if (itemQueryOrderBy) {
                    }
                }

                //Todo: result comic
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (state.totalResults != 0) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                androidx.compose.material3.Text(
                                    text = "Results: " + state.totalResults.toString(),
                                    color = MaterialTheme.colorScheme.inverseSurface,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .padding(bottom = 10.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    if (state.isSearchLoading) {
                        repeat(2) {
                            NormalComicCardShimmerLoading()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    } else {
                        state.searchResults.forEach { comic ->
                            SearchResultItem(
                                comic = comic,
                                advancedSearchViewModel = advancedSearchViewModel
                            )
                        }
                    }
                }

            }
        }

    }
}

@Composable
fun showTitle(
    title: String,
    onShowClick: (Boolean) -> Unit,
    itemShowBoolean: Boolean,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .padding(8.dp)
        )
        Button(
            onClick = { onShowClick(!itemShowBoolean) },
            modifier = Modifier
                .wrapContentSize()
                .width(100.dp)
                .height(40.dp)
                .scale(0.7f)
                .offset(x = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (itemShowBoolean) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
            )
        ) {
            Text(
                text = if (itemShowBoolean) "Hide" else "Show",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }
    }
}




