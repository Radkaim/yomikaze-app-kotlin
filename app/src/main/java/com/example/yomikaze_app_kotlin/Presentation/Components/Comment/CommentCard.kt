package com.example.yomikaze_app_kotlin.Presentation.Components.Comment

import androidx.compose.runtime.Composable

@Composable
fun CommentCard(
    commentId: Long,
    content: String,
    authorName: String,

    roleName:String,
    isOwnComment: Boolean? = false,
    onEditClicked: () -> Unit? = {},
    onDeleteClicked: () -> Unit? = {},
    onClicked: () -> Unit? = {}
) {

//    Surface(
//        modifier = modifier.then(
//            if (isDeleted) {
//                Modifier
//                    .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.small)
//                    .clickable {
//                        onClicked()
//                    }
//
//            } else {
//                Modifier
//            }),
//        color = backgroundColor,
//    ) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .offset(x = (-1).dp)
//                .clickable {
//                    onClicked()
//                }
//        ) {
//            if (isDeleted) {
//                Box(
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(
//                            top = 9.dp,
//                            end = 8.dp
//                        )
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_delete),
//                        contentDescription = "Delete Icon",
//                        tint = MaterialTheme.colorScheme.onPrimary,
//                        modifier = Modifier
//                            .width(15.dp)
//                            .height(17.dp)
//                            .clickable {
//                                //TODO: Implement delete comic
//                            }
//                    )
//                }
//
//            }
//            val rowSpaceBy = if (isSearch) 45 else 15
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(rowSpaceBy.dp)
//            ) {}
//        }}
}