package com.example.yomikaze_app_kotlin.Presentation.Components.Comment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu.MenuOptions
import com.example.yomikaze_app_kotlin.R

@Composable
fun CommentCard(
    commentId: Long,
    content: String,
    authorName: String,
    authorImage: String,

    roleName: String,
    isOwnComment: Boolean? = false,
    onEditClicked: () -> Unit? = {},
    onDeleteClicked: () -> Unit? = {},
    onClicked: () -> Unit? = {}
) {
    var showPopupMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf<Int?>(null) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small, clip = true)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small
            )
            .clickable { onClicked() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(10.dp)

        ) {
            //image and role
            Column {
                //Author Image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(authorImage)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder),
                    contentDescription = "Comic Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(100.dp)
                        )
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(100.dp))
                )

                Text(
                    text = roleName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            //name and content
            Column {
                //Author Name
                Text(
                    text = authorName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                //Content
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 15.sp,
                    modifier = Modifier.width(300.dp)
                )

                // created at and likes
                Row {
                    Text(
                        text = "1 day ago",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )


                }
            }


        }

        //Options Icon
        val listTitlesOfComicMenuOption = listOf(
            MenuOptions("Edit", "edit_comment_route", R.drawable.ic_edit),
            MenuOptions("Report", "report_comment_route", R.drawable.ic_report),
            MenuOptions("Delete", "delete_comment_route", R.drawable.ic_delete),
        )
        Box {
            IconButton(
                onClick = { showPopupMenu = true },
                modifier = Modifier
                    .padding(start = 340.dp)
                //.offset(y = (10).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = "More option menu",
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .offset(y = (-90).dp)
                    .padding(start = 200.dp)
            ) {
                DropdownMenu(
                    expanded = showPopupMenu,
                    onDismissRequest = { showPopupMenu = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.onSurface),
                ) {
                    listTitlesOfComicMenuOption.forEachIndexed { index, menuOptions ->
                        DropdownMenuItem(
                            onClick = {
                                showPopupMenu = false
                                when (menuOptions.route) {
                                    "edit_comment_route" -> showDialog = 1
                                    "report_comment_route" -> showDialog = 2
                                    "delete_comment_route" -> showDialog = 3

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

            }
            if (showDialog != null) {
                when (showDialog) {
                    1 -> {
//                        EditDialogComponent(
//                            key = categoryId,
//                            title = "Edit Personal Category ‘s Name",
//                            value = value,
//                            onDismiss = { showDialog = 0 },
//                            viewModel = libraryViewModel
//                        )
                    }

                    2 -> {}

                    3 -> {
//                        DeleteConfirmDialogComponent(
//                            key = categoryId,
//                            value = value,
//                            title = "Are you sure you want to delete this category?",
//                            onDismiss = { showDialog = 0 },
//                            viewModel = libraryViewModel
//                        )

                    }

                }
            }
        }
    }
}