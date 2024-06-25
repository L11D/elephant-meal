package com.example.elephantmeal.week_screen.view_model

import com.example.elephantmeal.R
import com.example.elephantmeal.day_screen.domain.Mealtime
import com.example.elephantmeal.week_screen.domain.WeekDate

data class WeekUiState(
    val weekStart: WeekDate = WeekDate(
        day = 1,
        monthStringResource = R.string.of_january
    ),
    val weekEnd: WeekDate = WeekDate(
        day = 7,
        monthStringResource = R.string.of_january
    ),
    val weekRation: List<Mealtime> = listOf()
)
