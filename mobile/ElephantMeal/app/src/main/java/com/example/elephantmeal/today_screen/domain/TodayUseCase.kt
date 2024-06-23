package com.example.elephantmeal.today_screen.domain

import androidx.compose.ui.res.stringResource
import com.example.elephantmeal.R

class TodayUseCase {
    // Перевод номера дня недели в его название
    fun getDayOfWeekName(dayOfWeek: Int) : Int {
        return when (dayOfWeek) {
            1 -> R.string.monday_lowercase
            2 -> R.string.tuesday_lowercase
            3 -> R.string.wednesday_lowercase
            4 -> R.string.thursday_lowercase
            5 -> R.string.friday_lowercase
            6 -> R.string.saturday_lowercase
            7 -> R.string.sunday_lowercase
            else -> R.string.empty
        }
    }
}