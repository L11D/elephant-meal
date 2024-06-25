package com.example.elephantmeal.day_screen.view_model

import com.example.elephantmeal.day_screen.domain.Mealtime
import java.time.LocalDate

data class DayUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedWeek: MutableList<LocalDate> = mutableListOf(),
    val weekRation: List<Mealtime> = listOf(),
    val dayRation: List<Mealtime> = listOf()
)
