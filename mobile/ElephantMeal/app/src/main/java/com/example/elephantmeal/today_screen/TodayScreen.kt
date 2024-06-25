package com.example.elephantmeal.today_screen

import androidx.compose.runtime.Composable
import com.example.elephantmeal.day_screen.presentation.DayScreen
import com.example.elephantmeal.week_screen.WeekScreen

@Composable
fun TodayScreen(
    onHomeClick: () -> Unit,
    onDayClick: () -> Unit,
    onMenuClick: (isWeekModeSelected: Boolean) -> Unit,
    onWeekClick: () -> Unit,
    isWeekModeSelected: Boolean
) {
    if (isWeekModeSelected) {
        WeekScreen()
    }
    else {
        DayScreen(
            onHomeClick = onHomeClick,
            onDayClick = onDayClick,
            onMenuClick = onMenuClick,
            onWeekClick = onWeekClick
        )
    }
}