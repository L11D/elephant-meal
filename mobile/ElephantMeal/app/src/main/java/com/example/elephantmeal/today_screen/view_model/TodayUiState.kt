package com.example.elephantmeal.today_screen.view_model

import java.time.LocalDate

data class TodayUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedWeek: MutableList<LocalDate> = mutableListOf()
)
