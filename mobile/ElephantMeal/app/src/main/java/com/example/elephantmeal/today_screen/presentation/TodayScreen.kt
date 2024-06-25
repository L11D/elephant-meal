package com.example.elephantmeal.today_screen.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.elephantmeal.day_screen.presentation.DayScreen
import com.example.elephantmeal.today_screen.view_model.TodayViewModel
import com.example.elephantmeal.week_screen.presentation.WeekScreen
import java.time.LocalDate

// Экран просмотра расписания рациона
@Composable
fun TodayScreen(
    onHomeClick: () -> Unit,
    onMenuClick: (isWeekModeSelected: Boolean) -> Unit,
    isWeekModeSelected: Boolean,
    viewModel: TodayViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.setWeekMode(isWeekModeSelected)
    }

    val state by viewModel.state.collectAsState()

    // Экран просмотра расписания рациона на неделю
    if (state.isWeekModeSelected) {
        WeekScreen(
            onHomeClick = onHomeClick,
            onMenuClick = onMenuClick,
            onDayClick = {
                viewModel.onDayModeSelected()
            },
            selectedDate = state.selectedDate
        )
    }
    // Экран просмотра расписания рациона на день
    else {
        DayScreen(
            onHomeClick = onHomeClick,
            onMenuClick = onMenuClick,
            onWeekClick = { selectedDate ->
                viewModel.onWeekModeSelected(selectedDate)
            }
        )
    }
}