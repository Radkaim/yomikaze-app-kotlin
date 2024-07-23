package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Domain.Models.Tag
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NoNetworkAvailable
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.NormalComicCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.TagItemShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.QueryByComicNameTextField
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.SearchResultItem


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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                //TODO: Reset all value

                Button(
                    onClick = { advancedSearchViewModel.resetState() },
                    modifier = Modifier
                        .wrapContentSize()
                        .width(120.dp)
                        .height(40.dp)
                        .scale(0.7f)
                        .offset(x = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.outline,
                    )
                ) {
                    Text(
                        text = "Reset All",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }
                //TODO: Hide all item
                Button(
                    onClick = { hideAll() },
                    modifier = Modifier
                        .wrapContentSize()
                        .width(120.dp)
                        .height(40.dp)
                        .scale(0.7f)
                        .offset(x = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (hideAllColorCheck) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.onPrimary,
                    )
                ) {
                    Text(
                        text = "Hide All",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }
                //TODO: Search button
                Button(
                    onClick = { advancedSearchViewModel.performAdvancedSearch() },
                    modifier = Modifier
                        .wrapContentSize()
                        .width(120.dp)
                        .height(40.dp)
                        .scale(0.7f)
                        .offset(x = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                ) {
                    Text(
                        text = "Search",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp), // 15.dp space between each card
                modifier = Modifier
                    .padding(
                        top = 15.dp,
                        start = 4.dp,
                        end = 4.dp,
                        bottom = 4.dp
                    ) // Optional padding for the entire list
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp), // 8.dp space between each item
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp)
                ) {

                    //TODO: search by comic name
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Comic Name",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
//                                   .clickable { itemQueryComicName = !itemQueryComicName }
                            )
                            Button(
                                onClick = { itemQueryComicName = !itemQueryComicName },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemQueryComicName) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemQueryComicName) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clickable { itemQueryComicName = !itemQueryComicName }
                                )
                            }
                        }
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Author",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { itemQueryAuthor = !itemQueryAuthor },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemQueryAuthor) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemQueryAuthor) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier

                                )
                            }
                        }
                        if (itemQueryAuthor) {

                        }
                    }

                    //Todo: search by publisher
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Author",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { itemQueryPublisher = !itemQueryPublisher },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemQueryPublisher) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemQueryPublisher) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier

                                )
                            }
                        }
                        if (itemQueryPublisher) {

                        }
                    }

                    //Todo: search by status
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Status",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { itemQueryStatus = !itemQueryStatus },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemQueryStatus) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemQueryStatus) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier

                                )
                            }
                        }
                        if (itemQueryStatus) {

                        }
                    }

                    //Todo: search by From - To Date
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "From - To Date",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { itemQueryFromToDate = !itemQueryFromToDate },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemQueryFromToDate) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemQueryFromToDate) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }
                        }
                        if (itemQueryFromToDate) {
                        }
                    }

                    //Todo: search by From - To Total Chapter
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "From - To Total Chapter",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { itemFromToTotalChapter = !itemFromToTotalChapter },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemFromToTotalChapter) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemFromToTotalChapter) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }
                        }
                        if (itemFromToTotalChapter) {
                        }
                    }

                    //Todo: search by From - To Total View
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "From - To Total View",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { itemFromToTotalView = !itemFromToTotalView },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemFromToTotalView) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemFromToTotalView) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }
                        }
                        if (itemFromToTotalView) {
                        }
                    }

                    //Todo: search by From - To Average Rating
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "From - To Average Rating",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { itemFromToAverageRating = !itemFromToAverageRating },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemFromToAverageRating) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemFromToAverageRating) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }
                        }
                        if (itemFromToAverageRating) {
                        }
                    }

                    //Todo: search by From - To Follow
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "From - To Follow",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { itemFromToTotalFollow = !itemFromToTotalFollow },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemFromToTotalFollow) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemFromToTotalFollow) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }
                        }
                        if (itemFromToTotalFollow) {
                        }
                    }

                    //Todo: search by tag
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Tags",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
//                                   .clickable { itemQueryComicName = !itemQueryComicName }
                            )
                            Button(
                                onClick = { itemQueryTag = !itemQueryTag },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemQueryTag) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemQueryTag) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }
                        }
                        if (itemQueryTag) {
                            val tagId = 1L..20L
                            LaunchedEffect(key1 = itemQueryTag) {
                                advancedSearchViewModel.getTags()
                            }
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Exclude/Include Mode",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
//                                   .clickable { itemQueryComicName = !itemQueryComicName }
                            )
                            Button(
                                onClick = { itemExcludeIncludeMode = !itemExcludeIncludeMode },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemExcludeIncludeMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemExcludeIncludeMode) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }
                        }
                        if (itemExcludeIncludeMode) {
                        }
                    }

                    //Todo: search by order by
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Order By",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Button(
                                onClick = { itemQueryOrderBy = !itemQueryOrderBy },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .width(100.dp)
                                    .height(40.dp)
                                    .scale(0.7f)
                                    .offset(x = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (itemQueryOrderBy) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer,
                                )
                            ) {
                                Text(
                                    text = if (itemQueryOrderBy) "Hide" else "Show",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }
                        }
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
                        }else{
                            state.searchResults.forEach { comic ->
                                SearchResultItem(comic = comic, advancedSearchViewModel = advancedSearchViewModel)
                            }
                        }
                    }

                }
            }
        }
    }
}

//@Composable
//fun titleSearch(
//    onClick: () -> Unit,
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(
//            text = "Comic Name",
//            fontSize = 15.sp,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.primaryContainer,
//            modifier = Modifier
//                .padding(8.dp)
////                                   .clickable { itemQueryComicName = !itemQueryComicName }
//        )
//        Button(
//            onClick = { onClick()  },
//            modifier = Modifier.wrapContentSize().width(100.dp).height(40.dp).scale(0.7f).offset(x = 20.dp),
//        ) {
//            Text(
//                text = if (itemQueryComicName) "Hide" else "Show",
//                fontSize = 20.sp,
//                color = MaterialTheme.colorScheme.onSurface,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .clickable { onClick()}
//            )
//        }
//    }
//}


@Composable
fun TagCheckbox(tag: Long, tagName: String, viewModel: AdvancedSearchViewModel) {
    val tagStates by viewModel.tagStates.collectAsState()
    val tagState = tagStates[tag] ?: TagState.NONE

    Row(verticalAlignment = Alignment.CenterVertically) {
        CustomCheckbox(
            tag = tag,
            tagName = tagName,
            tagState = tagState,
            onCheckedChange = {
                viewModel.toggleTagState(tag)
                //cal when search click
                viewModel.updateQueryTags()
            }
        )
    }
}

@Composable
fun CustomCheckbox(
    tag: Long,
    tagName: String,
    tagState: TagState,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable { onCheckedChange(tagState != TagState.INCLUDE) }
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
                .clickable {
                    onCheckedChange(tagState != TagState.INCLUDE) // Toggle state
                },
            contentAlignment = Alignment.Center
        ) {
            when (tagState) {
                TagState.INCLUDE -> Icon(
                    imageVector = Icons.Filled.Check, // Dấu tick
                    contentDescription = "Include",
                    tint = Color.Green
                )

                TagState.EXCLUDE -> Icon(
                    imageVector = Icons.Filled.Close, // Dấu x
                    contentDescription = "Exclude",
                    tint = Color.Red
                )

                else -> {}
            }
        }
        TagComponent(status = tagName)
    }
}

@Composable
fun TagList(viewModel: AdvancedSearchViewModel, tags: List<Tag>) {

    val firstHalf = tags.take((tags.size + 1) / 2)
    val secondHalf = tags.takeLast(tags.size / 2)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            firstHalf.forEach { tag ->
                TagCheckbox(tag = tag.tagId, tagName = tag.name, viewModel = viewModel)
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            secondHalf.forEach { tag ->
                TagCheckbox(tag = tag.tagId, tagName = tag.name, viewModel = viewModel)
            }
        }
    }
}

