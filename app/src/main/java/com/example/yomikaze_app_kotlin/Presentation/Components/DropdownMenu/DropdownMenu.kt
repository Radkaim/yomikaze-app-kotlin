package com.example.yomikaze_app_kotlin.Presentation.Components.DropdownMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


data class MenuOptions(
    val title: String,
    val route: String,
    val icon: Int,
)

data class ChooseOptionState(
    val route: String,
)

@Composable
fun DropdownMenuCustom(
    listTitles: List<MenuOptions>,
    isExpanded: Boolean,
    navController: NavController,
    onDismiss: () -> Unit = {},
    onOptionSelected: (chooseOptionState: ChooseOptionState) -> Unit // Callback để thông báo về sự thay đổi của chooseOptionState
) {

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSurface)
            .offset(x = (1).dp, y = (0).dp)

    ) {
        // use foreach
        listTitles.forEachIndexed { index, tabs ->

            //if tabs is the last one, remove a line after that tab, and
            DropdownMenuItemCustom(
                title = tabs.title,
                icon = tabs.icon,
                onClick = {
                    // Gọi callback để thông báo về sự thay đổi của chooseOptionState khi một mục được chọn
                    onOptionSelected(ChooseOptionState(tabs.route)) // Đây là một ví dụ, bạn cần cập nhật tham số tùy thuộc vào cấu trúc của chooseOptionState
                    onDismiss()
                },
                onOptionSelected = onOptionSelected // Truyền callback vào DropdownMenuItemCustom
            )
            if (index < listTitles.size - 1) {
                Divider(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun DropdownMenuItemCustom(
    title: String,
    icon: Int,
    onClick: () -> Unit,
 // chooseOptionState: ChooseOptionState, // Thêm tham số này để truyền giá trị hiện tại của chooseOptionState
    onOptionSelected: (chooseOptionState: ChooseOptionState) -> Unit // Callback để thông báo về sự thay đổi của chooseOptionState
) {
    DropdownMenuItem(onClick = {
        onClick()
        // Khi người dùng chọn một mục từ menu, gọi callback để thông báo về sự thay đổi của chooseOptionState
    })
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .height(15.dp)
                .width(125.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                painterResource(id = icon),
                contentDescription = "Icon $title",
                modifier = Modifier
                    .width(17.dp)
                    .height(17.dp),
                tint = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
            )
            Text(
                text = title,
                color = MaterialTheme.colorScheme.inverseSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }

}

