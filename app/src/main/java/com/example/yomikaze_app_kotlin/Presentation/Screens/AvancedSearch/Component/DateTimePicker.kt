package com.example.yomikaze_app_kotlin.Presentation.Screens.AvancedSearch.Component


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SampleDatePickerView() {
    val state = rememberDateRangePickerState()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .background(Color.White)
            ) {
                DateRangePickerSample(state)
                Button(
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp)
                ) {
                    Text("Done", color = Color.White)
                }
            }
        },
        content = {
            Column {
                Button(onClick = {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                }, modifier = Modifier.padding(16.dp)) {
                    Text(text = "Open Date Picker")
                }
                Text(text = "Start Date:" + if (state.selectedStartDateMillis != null) state.selectedStartDateMillis?.let {
                    getFormattedDateForRequest(
                        it
                    )
                } else "")
                Log.d(
                    "SampleDatePickerView",
                    "Start Date: ${
                        state.selectedStartDateMillis?.let {
                            getFormattedDateForRequest(it)
                        }
                    }")
                Text(text = "End Date:" + if (state.selectedEndDateMillis != null) state.selectedEndDateMillis?.let {
                    getFormattedDateForRequest(
                        it
                    )
                } else "")
            }
        },
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    )
}

fun getFormattedDateForRequest(timeInMillis: Long): String {
//    val calender = Calendar.getInstance()
//    calender.timeInMillis = timeInMillis
//    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//    return dateFormat.format(calender.timeInMillis)

    val instant = Instant.ofEpochMilli(timeInMillis)
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneOffset.UTC)
    return formatter.format(instant)
}

fun getFormattedDateForDisplay(timeInMillis: Long): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(calender.timeInMillis)
}

fun dateValidator(): (Long) -> Boolean {
//    return {
//            timeInMillis ->
//        val endCalenderDate = Calendar.getInstance()
//        endCalenderDate.timeInMillis = timeInMillis
//        endCalenderDate.set(Calendar.DATE, Calendar.DATE + 30)
//        timeInMillis > Calendar.getInstance().timeInMillis && timeInMillis < endCalenderDate.timeInMillis
//    }
    return { timeInMillis ->
//        val endCalendarDate = Calendar.getInstance()
//        endCalendarDate.timeInMillis = timeInMillis
//        endCalendarDate.add(Calendar.DATE, 30)
        // Allow dates in the past
        true
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerSample(state: DateRangePickerState) {
    DateRangePicker(
        state,
        modifier = Modifier,
        dateFormatter = DatePickerFormatter("dd MM yyyy", "dd MM yyyy", "dd MM yyyy"),
        dateValidator = dateValidator(),
        title = {
            Text(
                text = "Select date range for searching by published date", modifier = Modifier
                    .padding(16.dp)
            )
        },
        headline = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(Modifier.weight(1f)) {
                    (if (state.selectedStartDateMillis != null) state.selectedStartDateMillis?.let {
                        getFormattedDateForDisplay(
                            it
                        )
                    } else "Start Date")?.let { Text(text = it) }
                }
                Box(Modifier.weight(1f)) {
                    (if (state.selectedEndDateMillis != null) state.selectedEndDateMillis?.let {
                        getFormattedDateForDisplay(
                            it
                        )
                    } else "End Date")?.let { Text(text = it) }
                }
                Box(Modifier.weight(0.2f)) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Okk")
                }

            }
        },
        showModeToggle = true,
        colors = DatePickerDefaults.colors(
            dayContentColor = MaterialTheme.colorScheme.primaryContainer,

            headlineContentColor = MaterialTheme.colorScheme.primary,
            weekdayContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),

            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.error,
            subheadContentColor = MaterialTheme.colorScheme.primary,
            yearContentColor = MaterialTheme.colorScheme.primary,
            currentYearContentColor = MaterialTheme.colorScheme.primary,

            selectedYearContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledDayContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
            disabledSelectedDayContainerColor = Color.Black,
            todayDateBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),

            dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
            dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onPrimary,

            selectedDayContainerColor = MaterialTheme.colorScheme.onSecondary,


            titleContentColor = MaterialTheme.colorScheme.primary,


            selectedYearContentColor = MaterialTheme.colorScheme.primary,


            ),

        )
}
