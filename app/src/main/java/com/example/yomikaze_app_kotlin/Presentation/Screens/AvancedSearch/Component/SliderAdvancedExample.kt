package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SliderAdvancedExample(
    onValueFromChange: (Float) -> Unit = {},
    onValueToChange: (Float) -> Unit = {},
    fromValueChange: Float = 0f,
    toValueChange: Float = 5f,
    defaultFromValue: Float,
    defaultToValue: Float,
    steps: Int = 5,
    resetSlider: Boolean = false,
    isInteger: Boolean = false
) {
    var sliderPosition by remember { mutableStateOf(fromValueChange..toValueChange) }
    Log.d("SliderAdvancedExample", "fromValueChange: $fromValueChange")
    Log.d("SliderAdvancedExample", "toValueChange: $toValueChange")

    //reset slider
    if (resetSlider) {
        sliderPosition = defaultFromValue..defaultToValue
        sliderPosition = remember {
            mutableStateOf(fromValueChange..toValueChange)
        }.value

    }

    Column {
        RangeSlider(
            value = sliderPosition,
            steps = steps,
            onValueChange = { range -> sliderPosition = range },
            valueRange = defaultFromValue..defaultToValue,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
//                // viewModel.updateSelectedSliderValue(sliderPosition)
//                val roundedStart = String.format("%.1f", sliderPosition.start).toFloat()
//                val roundedEnd = String.format("%.0f", sliderPosition.endInclusive).toFloat()
                onValueFromChange(sliderPosition.start)
                onValueToChange(sliderPosition.endInclusive)
            },
        )
        //first value làm tròn số sau 1 dấu phẩy


        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            if (isInteger) {
                Text(text = "From: " + (sliderPosition.start.toInt()), color = MaterialTheme.colorScheme.primaryContainer,)
                Text(text = "To: " + (sliderPosition.endInclusive.toInt()), color = MaterialTheme.colorScheme.primaryContainer,)
            } else {
                Text(text = "From: " + String.format("%.1f", sliderPosition.start), color = MaterialTheme.colorScheme.primaryContainer,)

                Text(text = "To: " + String.format("%.1f", sliderPosition.endInclusive), color = MaterialTheme.colorScheme.primaryContainer,)
            }
        }
    }
}

fun changeTextFormat(number: Int): String {
    var numberAfterDivide = number
    return when (number) {
        in 1000..999000 -> {
            numberAfterDivide /= 1000
            "$numberAfterDivide" + "K"
        }

        in 1000000..999000000 -> {
            numberAfterDivide /= 1000000
            "$numberAfterDivide" + "M"
        }

        in 1000000000..999000000000 -> {
            numberAfterDivide /= 1000000000
            "$numberAfterDivide" + "B"
        }

        else -> number.toString()
    }
}