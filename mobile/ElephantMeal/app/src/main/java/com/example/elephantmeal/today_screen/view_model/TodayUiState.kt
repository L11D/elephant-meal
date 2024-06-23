package com.example.elephantmeal.today_screen.view_model

import com.example.elephantmeal.today_screen.domain.Mealtime
import java.time.LocalDate

data class TodayUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedWeek: MutableList<LocalDate> = mutableListOf(),
    val weekRation: List<Mealtime> = listOf(),
    val dayRation: List<Mealtime> = listOf()
)
