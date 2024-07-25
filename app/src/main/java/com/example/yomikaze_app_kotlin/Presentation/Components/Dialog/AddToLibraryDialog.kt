package com.example.yomikaze_app_kotlin.Presentation.Components.Dialog

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.ComponentRectangleLineLong
import com.example.yomikaze_app_kotlin.Presentation.Screens.Bookcase.Library.LibraryViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.ComicDetails.ComicDetailViewModel
import com.example.yomikaze_app_kotlin.R

@Composable
fun AddToLibraryDialog(
    comicId: Long,
    isInComic: Boolean? = true,
    comicDetailViewModel: ComicDetailViewModel? = null,
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    isFollowed: Boolean,
    onDismiss: () -> Unit
) {
//    val libraryViewModel = hiltViewModel<LibraryViewModel>()
    val libraryState by libraryViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        libraryViewModel.getAllCategory()
    }
    LaunchedEffect(Unit) {
        libraryViewModel.getCategoriesOfComic(comicId = comicId)
    }


    val userCategories = (libraryState.categoryList!!)
//    Log.d("AddToLibraryDialog", "User categories1: $userCategories")

    val comicCategories = (libraryState.listCateComicIsIn)
//    Log.d("AddToLibraryDialog", "Comic categories: $comicCategories")

    // Danh sách các ID danh mục đã thêm vào truyện
    val comicCategoryIds = comicCategories.map { it.id }

//        Log.d("AddToLibraryDialog", "Comic category ids: $comicCategoryIds")

    // Danh sách các ID danh mục người dùng đã chọn để thêm vào
    var selectedCategories by remember { mutableStateOf(listOf<Long>()) }

    if (comicCategories.isNotEmpty()) {
        selectedCategories = comicCategoryIds
    }

    // Danh sách các ID danh mục người dùng chọn để loại bỏ
    val deselectedCategories = remember { mutableStateOf(listOf<Long>()) }


    LaunchedEffect(key1 = libraryState.isCreateCategorySuccess) {
        libraryViewModel.getAllCategory()
    }

    LaunchedEffect(key1 = libraryState.isRemoveComicFromCategorySuccess) {
        libraryViewModel.getAllCategory()
    }

    LaunchedEffect(key1 = libraryState.isAddComicToCategoryFirstTimeSuccess) {
        libraryViewModel.getAllCategory()
    }

    LaunchedEffect(key1 = libraryState.isAddComicToCategorySecondTimeSuccess) {
        libraryViewModel.getAllCategory()
    }

    LaunchedEffect(key1 = libraryState.isUnFollowComicSuccess) {
        libraryViewModel.getAllCategory()
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
                .clickable {
                    onDismiss()
                    if (isInComic == true) {
                        libraryViewModel.resetState()
                        comicDetailViewModel?.getComicDetailsFromApi(comicId = comicId)
                    }
                }
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
                        text = "Totals: ${libraryState.totalCategoryResults}",
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
                            if (libraryState.isCategoryLoading)
                                repeat(5) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    ComponentRectangleLineLong()
                                }
                        }
//                            Log.d("AddToLibraryDialog", "selectedCategories: $selectedCategories")
                        items(userCategories) { category ->
                            var isSelected by remember {
                                mutableStateOf(selectedCategories.contains(category.id))
                            }
//                            val colorSelected = if (isCurrentlySelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (isSelected) {
                                            selectedCategories = selectedCategories - category.id
                                            deselectedCategories.value =
                                                deselectedCategories.value + category.id
                                        } else {
                                            selectedCategories = selectedCategories + category.id
                                            deselectedCategories.value =
                                                deselectedCategories.value - category.id
                                        }
                                        isSelected = !isSelected
                                    }
                                    .padding(8.dp)
                            ) {
                                val colorSelected = if (isSelected) {
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
                                        fontSize = 16.sp,
                                    )
                                }
                                val icon = if (isSelected) {
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

                            if (!isFollowed
                                && selectedCategories.isNotEmpty()
                                && deselectedCategories.value.isEmpty()
                            ) {
                                libraryViewModel.addComicToLibraryFirstTime(
                                    comicId,
                                    selectedCategories
                                )
                            }

                            if (isFollowed && selectedCategories.isNotEmpty()) {
                                libraryViewModel.addComicToLibrarySecondTime(
                                    comicId,
                                    selectedCategories
                                )
                            }

                            if (deselectedCategories.value.isNotEmpty()
                            ) {
                                libraryViewModel.removeComicFromCategory(
                                    comicId,
                                    deselectedCategories.value
                                )
                            }

                            // if deselectedCategories.value.isNotEmpty() and deselectedCategories  = comicCategoryIds
                            // unFollowComic
                            if (deselectedCategories.value.isNotEmpty() && deselectedCategories.value.size == comicCategoryIds.size
                                && deselectedCategories.value.containsAll(comicCategoryIds) && selectedCategories.isEmpty()
                            ) {
                                libraryViewModel.unfollowComicFromLibrary(comicId)
                            }
                            onDismiss()
                            if (isInComic!!) {
                                libraryViewModel.resetState()
                                comicDetailViewModel?.getComicDetailsFromApi(comicId = comicId)
                            }
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