package com.example.yomikaze_app_kotlin.Presentation.Screens.Comment.ComicComment

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
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
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.AnimationIcon.LottieAnimationComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.SortComponent
import com.example.yomikaze_app_kotlin.Presentation.Components.Comment.CommentCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.UnNetworkScreen
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.CommentCardShimmerLoading
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

    var isSelected by remember { mutableStateOf(false) }
    var isReversed by remember { mutableStateOf(true) }


    val appPreference = AppPreference(context)
    // Lưu vị trí hiện tại trước khi thay đổi
    val lastVisibleItemIndex = remember { mutableStateOf(-1) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    //for delete main reply comment in reply comment detail view and then back to comment view
    LaunchedEffect(key1 = appPreference.mainReplyCommentIdDeleted) {
        if (appPreference.mainReplyCommentIdDeleted != 0L) {
            comicCommentViewModel.removeMainReplyCommentHasDeletedFromList(appPreference.mainReplyCommentIdDeleted)
        }
    }


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
            val (header, messages, chatBox) = createRefs()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 5.dp)
                    .constrainAs(header) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Comments: ${state.totalCommentResults.value}",
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
                        lastVisibleItemIndex.value = listState.firstVisibleItemIndex
                        Log.d("ComicCommentContent", "isReversed: ${listState.firstVisibleItemIndex}")

                    },
                    onnNewSortClick = {
                        isSelected = false
                        isReversed = true
                        lastVisibleItemIndex.value = listState.firstVisibleItemIndex
                        Log.d("ComicCommentContent", "isReversed: ${listState.firstVisibleItemIndex}")

                    }
                )
            }

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(15.dp), // 8.dp space between each item
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
                    .constrainAs(messages) {
                        top.linkTo(header.bottom)
                        bottom.linkTo(chatBox.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    },

            ) {

                if (state.isListComicCommentLoading) {
                    item {
                        repeat(6) {
                            CommentCardShimmerLoading()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                if (!state.isListComicCommentLoading && state.listComicComment.isNotEmpty()) {
                    val sortedList = if (isReversed) {
                        state.listComicComment.sortedByDescending { it.creationTime }
                    } else {
                        state.listComicComment.sortedBy { it.creationTime }
                    }
                    items(sortedList) { comment ->
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
                            isUserLogin = appPreference.isUserLoggedIn,
                            isAdmin = comicCommentViewModel.checkIsAdmin(),
                            totalLikes = comment.totalLikes.toLong(),
                            myReaction = comment.myReaction,
                            isReacted = comment.isReacted,
                            onLikeClick= {
                                if(appPreference.isUserLoggedIn) {
                                    comicCommentViewModel.reactComicCommentByComicId(
                                        commentId = comment.id,
                                        comicId = comicId,
                                        reactionType = "Like"
                                    )
                                }else{
                                    Toast.makeText(context, "Please sign in to like", Toast.LENGTH_SHORT).show()
                                }
                            },
                            totalDislikes = comment.totalDislikes.toLong(),
                            onDislikeClick = {
                                if(appPreference.isUserLoggedIn) {
                                    comicCommentViewModel.reactComicCommentByComicId(
                                        commentId = comment.id,
                                        comicId = comicId,
                                        reactionType = "Dislike"
                                    )
                                }else{
                                    Toast.makeText(context, "Please sign in to dislike", Toast.LENGTH_SHORT).show()
                                }
                            },
                            totalReplies = comment.totalReplies.toLong(),
                            onClicked = {
                                comicCommentViewModel.onNavigateToReplyCommentDetail(
                                    commentId = comment.id,
                                    comicId = comicId,
                                    authorName = comment.author.name
                                )
                            },
                            listCommonCommentReportResponse = state.listCommonCommentReportResponse,
                            onReportClick = { commentId, reasonId, reportContent ->
                                comicCommentViewModel.reportComment(
                                    commentId = commentId,
                                    reportReasonId = reasonId,
                                    reportContent = reportContent
                                )
                            },
                            comicCommentViewModel = comicCommentViewModel
                        )
                    }
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

            ChatBox(
                focusManager = focusManager,
                keyboardController = keyboardController,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(chatBox) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onSendChatClickListener = {
                    comicCommentViewModel.postComicCommentByComicId(
                        comicId = comicId,
                        content = it,
                    )
                },
                isLogin = comicCommentViewModel.checkUserIsLogin()
            )
        }

        // Khôi phục vị trí cuộn sau khi thay đổi
        LaunchedEffect(key1 = lastVisibleItemIndex.value) {
            if (lastVisibleItemIndex.value != -1) {
                Log.d("ComicCommentContent", "lastVisibleItemIndex: ${lastVisibleItemIndex.value}")
                listState.scrollToItem(lastVisibleItemIndex.value)
            }
        }

        LaunchedEffect(
            key1 = page.value,
            key2 = isReversed,
            key3 = state
        ) {


            Log.d("ComicCommentContent", "page: ${page.value}")
            if (page.value > state.currentPage.value && !loading.value) {
                loading.value = true
                comicCommentViewModel.getAllComicCommentByComicId(
                    comicId = comicId,
                    page.value,
                    orderBy = if (isReversed) "CreationTimeDesc" else "CreationTime"
                )
                loading.value = false
            }
        }
        // Sử dụng SideEffect để phát hiện khi người dùng cuộn tới cuối danh sách
        LaunchedEffect(key1 = listState, key2 = isReversed, key3 = page.value) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collectLatest { lastVisibleItemIndex ->
                    if (!loading.value && lastVisibleItemIndex != null && lastVisibleItemIndex >= state.listComicComment.size - 2) {
                        Log.d(
                            "ComicCommentContent",
                            "ComicCommentContent12: ${lastVisibleItemIndex} and ${state.listComicComment.size}"
                        )
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
                            Toast.makeText(context, "No comments left", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
        }
    }
}

@Composable
fun ChatBox(
    onSendChatClickListener: (String) -> Unit,
    modifier: Modifier,
    isLogin: Boolean,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    val canPost by remember { mutableStateOf(isLogin) }
    val textSize: Int = 1024
//    val focusManager = LocalFocusManager.current
//    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Row(
        modifier = modifier.then(Modifier.background(MaterialTheme.colorScheme.tertiary)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },

            label = {
                if (chatBoxValue.text.length > textSize) {
                    Text(
                        text = "Your comment is too long",
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
                } else {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = "",
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
                if (chatBoxValue.text.isNotEmpty()) {
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

                            )
                    }
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
            isError = chatBoxValue.text.length > textSize,   //check if the length of text is over 256 characters
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                backgroundColor = MaterialTheme.colorScheme.onErrorContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                trailingIconColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.7f),
                //for error
                errorCursorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                errorIndicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                errorLabelColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                errorTrailingIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
            ),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(30.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(30.dp)
                )
        )
        if (chatBoxValue.text.isNotEmpty()) {
            IconButton(
                onClick = {
                    if (chatBoxValue.text.isNotEmpty() && chatBoxValue.text.length <= textSize && canPost) {
                        onSendChatClickListener(chatBoxValue.text)
                        Log.d("ChatBox", "ChatBox: ${chatBoxValue.text}")
                    } else if (chatBoxValue.text.length > textSize && canPost) {
                        Toast.makeText(
                            context,
                            "Please comment less than $textSize characters",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@IconButton
                    } else if (!canPost) {
                        Toast.makeText(
                            context,
                            "Please sign in to comment",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@IconButton
                    }
                    chatBoxValue = TextFieldValue("")
                },
//            modifier = Modifier.padding(top = 15.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_send_cmt),
                    contentDescription = "Send",
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}