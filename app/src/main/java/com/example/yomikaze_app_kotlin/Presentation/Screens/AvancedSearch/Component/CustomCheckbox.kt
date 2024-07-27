package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yomikaze_app_kotlin.Domain.Models.Tag
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.AdvancedSearchViewModel
import com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.TagState

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
                .border(1.dp, MaterialTheme.colorScheme.onSecondaryContainer, RoundedCornerShape(4.dp))
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


