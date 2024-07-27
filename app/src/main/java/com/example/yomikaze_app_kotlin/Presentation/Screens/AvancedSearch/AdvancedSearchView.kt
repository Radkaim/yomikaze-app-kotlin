package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
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
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.AuthorsManager
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.DateRangePickerSample
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.InputNumberTextField
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.OrderByOptionsDropdownMenu
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.QueryByValueTextField
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.ResetButton
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.SearchResultItem
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.SliderAdvancedExample
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.StatusOptionMenuDropdown
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.TagFilterModeSelector
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.TagList
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.getFormattedDateForDisplay
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component.getFormattedDateForRequest
import kotlinx.coroutines.launch


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
            comicNameSearchText = comicNameSearchText ?: "",
            advancedSearchViewModel = advancedSearchViewModel,
            state = state
        )
    } else {
        NoNetworkAvailable()
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdvancedSearchContent(
    navController: NavController,
    comicNameSearchText: String,
    advancedSearchViewModel: AdvancedSearchViewModel,
    state: AdvancedSearchState
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    //work
    var itemQueryComicName by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (comicNameSearchText.isNotEmpty() && comicNameSearchText != "null-null-null") {
            advancedSearchViewModel.updateQueryByComicName(comicNameSearchText)
            advancedSearchViewModel.performAdvancedSearch()
            itemQueryComicName = true
        }
    }


    LaunchedEffect(Unit) {
        advancedSearchViewModel.getTags()
    }
//   advancedSearchViewModel.performAdvancedSearch()


    // item to show

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

    var resetSliderAverage by remember { mutableStateOf(false) }
    var resetSliderTotalView by remember { mutableStateOf(false) }
    var resetSliderTotalFollow by remember { mutableStateOf(false) }
    var resetSliderTotalChapter by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = resetSliderAverage)
    {
        if (resetSliderAverage) {
            resetSliderAverage = false
        }
    }
    LaunchedEffect(key1 = resetSliderTotalView)
    {
        if (resetSliderTotalView) {
            resetSliderTotalView = false
        }
    }

    LaunchedEffect(key1 = resetSliderTotalFollow)
    {
        if (resetSliderTotalFollow) {
            resetSliderTotalFollow = false
        }
    }

    LaunchedEffect(key1 = resetSliderTotalChapter)
    {
        if (resetSliderTotalChapter) {
            resetSliderTotalChapter = false
        }
    }
    var dateState = rememberDateRangePickerState()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    //reset published date
    fun resetPublishedDate() {
        dateState.setSelection(
            startDateMillis = null,
            endDateMillis = null
        )
    }

    val hintForComicName =
        "Please enter the name of the comic you want to search for."
    // hint of function search
    val hintForInclusionModeExclusionMode =
        "Use the Inclusion Mode to select how tags should be combined in the search results: \"And\" will include comics that have all selected tags, while \"Or\" will include comics with any of the selected tags. Use the Exclusion Mode to exclude comics based on tags: \"And\" will exclude comics that have all selected tags, and \"Or\" will exclude comics with any of the selected tags"
    val hintForOrderBy =
        "Select the order in which the search results should be displayed. The default order is by relevance, but you can also sort by it options in ascending or descending options."
    val hintForTag =
        "Select tags to filter the search results. You can select multiple tags to narrow down the search results. One Click to include that tag must be in comic, Double Click to exclude that tag must not be in comic."
    val hintForAuthor =
        "Enter the name of the author you want to search for."
    val hintForPublisher =
        "Enter the name of the publisher you want to search for."
    val hintForStatus =
        "Select the status of the comics you want to search for. You can choose from ongoing, completed, or hiatus."
    val hintForFromToDate =
        "Select the date range for the comics you want to search for. You can choose a start date and an end date to filter the search results."
    val hintForTotalChapter =
        "Select the range of total chapters for the comics you want to search for. You can choose a minimum and maximum number of chapters to filter the search results."
    val hintForTotalView =
        "Select the range of total views for the comics you want to search for. You can choose a minimum and maximum number of views to filter the search results."
    val hintForAverageRating =
        "Select the range of average ratings for the comics you want to search for. You can choose a minimum and maximum rating to filter the search results."
    val hintForTotalFollow =
        "Select the range of total follows for the comics you want to search for. You can choose a minimum and maximum number of follows to filter the search results."


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

    fun showAll() {
        itemQueryComicName = true
        itemQueryAuthor = true
        itemQueryPublisher = true
        itemQueryStatus = true
        itemQueryFromToDate = true
        itemFromToTotalChapter = true
        itemFromToTotalView = true
        itemFromToAverageRating = true
        itemFromToTotalFollow = true
        itemQueryTag = true
        itemExcludeIncludeMode = true
        itemQueryOrderBy = true
    }


    //reset all value
    fun resetAll() {

        advancedSearchViewModel.resetState()
        resetPublishedDate()

        resetSliderAverage = true
        resetSliderTotalView = true
        resetSliderTotalFollow = true
        resetSliderTotalChapter = true
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
                title = "Advanced Search",
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
                                resetAll()
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
                                if (hideAllColorCheck) showAll() else hideAll()
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = if (hideAllColorCheck) "Show All" else "Hide All",
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
            ModalBottomSheetLayout(
                sheetState = bottomSheetState,
                sheetContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(700.dp)
                            .background(Color.White)
                    ) {
                        DateRangePickerSample(dateState)
                    }
                },
                scrimColor = Color.Black.copy(alpha = 0.5f),
                sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp), // 8.dp space between each item
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(bottom = 80.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        },

                    ) {

                    //TODO: search by comic name
                    item {
                        showTitle(
                            title = "Comic Name",
                            onShowClick = { itemQueryComicName = it },
                            itemShowBoolean = itemQueryComicName
                        )
                        if (itemQueryComicName) {
                            ResetButton(
                                onClick = {  advancedSearchViewModel.updateQueryByComicName("") },
                                description = hintForComicName
                            )
                            QueryByValueTextField(
                                placeHolderTitle = "Enter Comic Name",
                                queryByValue = state.queryByComicName,
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
                            ResetButton(
                                onClick = { advancedSearchViewModel.resetAuthors() },
                                description = hintForAuthor
                            )
                            AuthorsManager(
                                authors = state.queryListAuthorsInput ?: emptyList(),
                                onAddAuthor = {
                                    advancedSearchViewModel.updateListAuthorsInput(it)
                                },
                                onRemoveAuthor = {
                                    advancedSearchViewModel.removeAuthor(it)
                                }
                            )
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
                            ResetButton(
                                onClick = { advancedSearchViewModel.updateQueryByPublisher("") },
                                description = hintForPublisher
                            )
                            QueryByValueTextField(
                                placeHolderTitle = "Enter Publisher Name",
                                queryByValue = state.queryByPublisher,
                                onValueChange = { advancedSearchViewModel.updateQueryByPublisher(it) },
                                onCLoseClicked = { advancedSearchViewModel.updateQueryByPublisher("") }
                            )
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
                            ResetButton(
                                onClick = { advancedSearchViewModel.resetStatus() },
                                description = hintForStatus
                            )
                            StatusOptionMenuDropdown(
                                viewModel = advancedSearchViewModel,
                            )
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
                            ResetButton(
                                onClick = {
                                    advancedSearchViewModel.resetPublishedDateValue()
                                    resetPublishedDate()
                                },
                                description = hintForFromToDate
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {

                                //start date
                                Column(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                ) {

                                    Text(
                                        text = "Start Date: " + if (dateState.selectedStartDateMillis != null) dateState.selectedStartDateMillis?.let {
                                            getFormattedDateForDisplay(
                                                it
                                            )
                                        } else "",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = MaterialTheme.colorScheme.primaryContainer,
//                                        textDecoration = TextDecoration.Underline
                                    )

                                    if (dateState.selectedStartDateMillis != null) dateState.selectedStartDateMillis?.let {
                                        advancedSearchViewModel.updateQueryFromPublishedDate(
                                            getFormattedDateForRequest(
                                                it
                                            )
                                        )
                                    } else ""

                                    //end date
                                    Text(
                                        text = "End Date: " + if (dateState.selectedEndDateMillis != null) dateState.selectedEndDateMillis?.let {
                                            getFormattedDateForDisplay(
                                                it
                                            )
                                        } else "",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = MaterialTheme.colorScheme.primaryContainer,
//                                        textDecoration = TextDecoration.Underline)
                                    )
                                    if (dateState.selectedEndDateMillis != null) dateState.selectedEndDateMillis?.let {
                                        advancedSearchViewModel.updateQueryToPublishedDate(
                                            getFormattedDateForRequest(
                                                it
                                            )
                                        )
                                    } else advancedSearchViewModel.updateQueryToPublishedDate("")
                                }
                                //button
                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            bottomSheetState.show()
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .width(150.dp)
                                        .height(60.dp)
                                        .scale(0.7f)
                                        .offset(x = 20.dp),
                                ) {
                                    Text(
                                        text = "Select Date",
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                    )
                                }
                            }
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
                            ResetButton(
                                onClick = {
                                    advancedSearchViewModel.resetTotalChaptersValue()
                                    resetSliderTotalChapter = true
                                },
                                description = hintForTotalChapter
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                SliderAdvancedExample(
                                    fromValueChange = state.queryFromTotalChapters?.toFloat() ?: 0f,
                                    toValueChange = state.queryToTotalChapters?.toFloat()
                                        ?: 2500.toFloat(),
                                    defaultFromValue = 0f,
                                    defaultToValue = 2500.toFloat(),
                                    steps = 1000,
                                    isInteger = true,
                                    resetSlider = resetSliderTotalChapter,
                                    onValueFromChange = {
                                        advancedSearchViewModel.updateQueryFromTotalChapters(
                                            it.toInt()
                                        )
                                    },
                                    onValueToChange = {
                                        advancedSearchViewModel.updateQueryToTotalChapters(
                                            it.toInt()
                                        )
                                    }
                                )
                            }
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
                            ResetButton(
                                onClick = {
                                    advancedSearchViewModel.resetTotalViewsValue()
                                    resetSliderTotalView = true
                                },
                                description = hintForTotalView
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                InputNumberTextField(
                                    defaultValueFrom = state.queryFromTotalViews?.toString() ?: "",
                                    defaultValueTo = state.queryToTotalViews?.toString() ?: "",
                                    onValueFromChange = {
                                        advancedSearchViewModel.updateQueryFromTotalViews(
                                            it
                                        )
                                    },
                                    onValueToChange = {
                                        advancedSearchViewModel.updateQueryToTotalViews(
                                            it
                                        )
                                    },
                                    isReset = resetSliderTotalView,
                                    focusManager = focusManager,
                                    keyboardController = keyboardController
                                )

                            }
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
                            ResetButton(
                                onClick = {
                                    advancedSearchViewModel.resetAverageRatingValue()
                                    resetSliderAverage = true
                                },
                                description = hintForAverageRating
                            )
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
                                    resetSlider = resetSliderAverage,
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
                            ResetButton(
                                onClick = {
                                    advancedSearchViewModel.resetTotalFollowsValue()
                                    resetSliderTotalFollow = true
                                },
                                description = hintForTotalFollow
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                InputNumberTextField(
                                    defaultValueFrom = state.queryFromTotalFollows?.toString()
                                        ?: "",
                                    defaultValueTo = state.queryToTotalFollows?.toString() ?: "",
                                    onValueFromChange = {
                                        advancedSearchViewModel.updateQueryFromTotalFollows(
                                            it
                                        )
                                    },
                                    onValueToChange = {
                                        advancedSearchViewModel.updateQueryToTotalFollows(
                                            it
                                        )
                                    },
                                    isReset = resetSliderTotalFollow,
                                    focusManager = focusManager,
                                    keyboardController = keyboardController
                                )

                            }
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
                                ResetButton(
                                    onClick = { advancedSearchViewModel.resetTags() },
                                    description = hintForTag
                                )
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
                            ResetButton(
                                onClick = { advancedSearchViewModel.resetInclusionExclusionMode() },
                                description = hintForInclusionModeExclusionMode
                            )
                            TagFilterModeSelector(
                                viewModel = advancedSearchViewModel
                            )
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
                            ResetButton(
                                onClick = { advancedSearchViewModel.resetOrderBy() },
                                description = hintForOrderBy
                            )
                            OrderByOptionsDropdownMenu(
                                viewModel = advancedSearchViewModel,
                            )
                        }
                    }

                    //Todo: result comic
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
//                        if (state.totalResults != 0) {
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
//                        }
                        }
                    }

                    item {
                        if (state.isSearchLoading) {
                            repeat(2) {
                                NormalComicCardShimmerLoading()
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
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
