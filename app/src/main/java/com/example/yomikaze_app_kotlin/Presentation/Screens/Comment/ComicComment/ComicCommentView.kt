package com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Comment.CommentCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ComicCommentView(
    navController: NavController,
    comicId: Long,
    comicName: String,
    comicCommentViewModel: ComicCommentViewModel = hiltViewModel()
) {
    val state by comicCommentViewModel.state.collectAsState()

    //set navController for viewModel
    comicCommentViewModel.setNavController(navController)
    if (CheckNetwork()) {
        ComicCommentContent(
            navController = navController,
            comicName = comicName,
            state = state,
            comicCommentViewModel = comicCommentViewModel,
            comicId = comicId
        )
    } else {
        UnNetworkScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ComicCommentContent(
    navController: NavController,
    comicName: String,
    comicId: Long,
    state: ComicCommentState,
    comicCommentViewModel: ComicCommentViewModel
) {

    val listState = rememberLazyListState()
    val context = LocalContext.current
    val page = remember { mutableStateOf(1) }
    val loading = remember { mutableStateOf(false) }

    var isSelected by remember { mutableStateOf(true) }
    var isReversed by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CustomAppBar(
                title = comicName,
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
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (messages, chatBox) = createRefs()
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
                    .wrapContentSize(Alignment.Center)
                    .fillMaxWidth()
                    .constrainAs(messages) {
                        top.linkTo(parent.top)
                        bottom.linkTo(chatBox.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
            ) {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp), // 8.dp space between each item
//                    modifier = Modifier.fillMaxWidth()

                ) {
                    stickyHeader {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Chapter: ${state.totalCommentResults.value}",
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
                                    page.value = 1
                                    comicCommentViewModel.resetState()
                                },
                                onnNewSortClick = {
                                    isSelected = false
                                    isReversed = true
                                    page.value = 1
                                    comicCommentViewModel.resetState()
                                }
                            )
                        }
                    }
                        items(state.listComicComment) { comment ->
                            CommentCard(
                                commentId = comment.id,
                                content = comment.content,
                                authorName = comment.author.name,
                                authorImage = (APIConfig.imageAPIURL.toString() + comment.author.avatar)
                                    ?: "",
                                roleName = comment.author.roles?.get(0) ?: "",
                                creationTime = comment.creationTime,
                                isOwnComment = true,//comment.author.id == 1L,
                                onEditClicked = {},
                                onDeleteClicked = {},
                                onClicked = {}
                            )
                        }

                    // Hiển thị một mục tải dữ liệu khi cần
                    item {
                        if (loading.value) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                LottieAnimationComponent(
                                    animationFileName = R.raw.loading, // Replace with your animation file name
                                    loop = true,
                                    autoPlay = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .scale(1.15f)
                                )
                            }
                        }
                    }
                }
            }
            ChatBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(chatBox) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onSendChatClickListener = {

                })
        }

        LaunchedEffect(
            key1 = page.value,
            key2 = isReversed
        ) {
          Log.d("ComicCommentContent", "ComicCommentContent: ${page.value}")
            if (page.value > state.currentPage.value && !loading.value) {
                loading.value = true
                comicCommentViewModel.getAllComicCommentByComicId(
                    comicId = comicId,
                    page.value,
                    orderBy = if (isReversed) "CreationTime" else "CreationTimeDesc"
                )
                loading.value = false
            }

        }
        // Sử dụng SideEffect để phát hiện khi người dùng cuộn tới cuối danh sách
        LaunchedEffect(key1 = listState, key2 = isReversed, key3 = page.value) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collectLatest { lastVisibleItemIndex ->
                    if (!loading.value && lastVisibleItemIndex != null && lastVisibleItemIndex >= state.listComicComment.size - 2) {
                        Log.d("ComicCommentContent", "ComicCommentContent12: ${lastVisibleItemIndex} and ${state.listComicComment.size}")
                        if (state.currentPage.value < state.totalPages.value) {
                            page.value++
                        }
                    }
                }
        }

        //make toast when reach the end of list
        LaunchedEffect(
            key1 = state.currentPage.value,
            key2 = state.totalPages.value,
            key3 = isSelected,
        ) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collectLatest { lastVisibleItemIndex ->
                    if (lastVisibleItemIndex != null && lastVisibleItemIndex == state.listComicComment.size && state.listComicComment.size > 5) {
                        if (state.currentPage.value == state.totalPages.value && state.totalPages.value != 0) {
                            Toast.makeText(context, "No comments left", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

}

@Composable
fun ChatBox(
    onSendChatClickListener: (String) -> Unit,
    modifier: Modifier
) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },

            label = {
                if (chatBoxValue.text.length > 1024) {
                    Text(
                        text = "Max 100 characters",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                } else if (chatBoxValue.text.isEmpty()) {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = "Say something...",
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.errorContainer
                    )
                }
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            trailingIcon = {
                IconButton(
                    //  modifier = Modifier.alpha(ContentAlpha.medium),
                    onClick = {
                        if (chatBoxValue.text.isNotEmpty()) {
                            chatBoxValue = TextFieldValue("")
                        } else {
                            focusManager.clearFocus() // Clear focus to hide the keyboard
                            keyboardController?.hide() // Hide the keyboard
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.7f)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = if (chatBoxValue.text.isEmpty()) ImeAction.Done else ImeAction.Default
            ),
            keyboardActions = KeyboardActions(
                // enter a new line when press enter
                onDone = {
                    if (chatBoxValue.text.isEmpty()) {
                        focusManager.clearFocus() // Clear focus to hide the keyboard
                        keyboardController?.hide() // Hide the keyboard
                    }
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
            ),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(50.dp)
                )
        )
        IconButton(
            onClick = {
                if (chatBoxValue.text.isNotEmpty()) {
                    onSendChatClickListener(chatBoxValue.text)
                    Log.d("ChatBox", "ChatBox: ${chatBoxValue.text}")
                    chatBoxValue = TextFieldValue("")
                }
            },
//            modifier = Modifier.padding(top = 15.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_send_cmt),
                contentDescription = "Send",
                tint = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}