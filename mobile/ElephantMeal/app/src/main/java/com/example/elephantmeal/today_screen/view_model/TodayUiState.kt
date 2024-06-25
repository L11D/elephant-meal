package com.example.elephantmeal.today_screen.view_model

import java.time.LocalDate

data class TodayUiState(
    val isWeekModeSelected: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now()
)
