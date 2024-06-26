package com.example.elephantmeal.week_screen.view_model

import com.example.elephantmeal.R
import com.example.elephantmeal.day_screen.domain.Mealtime
import com.example.elephantmeal.week_screen.domain.WeekDate
import java.time.LocalDate

data class WeekUiState(
    val week: List<WeekDate> = listOf(
        WeekDate(
            day = 1,
            monthStringResource = R.string.of_january,
            dayOfWeek = R.string.monday
        )
    ),
    val weekRation: List<Mealtime> = listOf(),
    val maxMealtimes: Int = 0,
    val isMealtimeDialogVisible: Boolean = false,
    val dialogMealtime: Mealtime = Mealtime(
        name = "Завтрак",
        caloric = 100,
        receipt = "Банан",
        startTime = "10:00",
        endTime = "10:20",
        dateTime = LocalDate.now()
    )
)
