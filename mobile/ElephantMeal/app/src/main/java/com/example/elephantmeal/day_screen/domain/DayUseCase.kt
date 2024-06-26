package com.example.elephantmeal.day_screen.domain

import com.example.elephantmeal.R
import java.time.LocalDate

class DayUseCase {
    // Получение расписания рациона на неделю
    fun getWeekRation(dates: List<LocalDate>): List<Mealtime> {
        return generateMealtime().filter {
            it.dateTime.dayOfYear >= dates[0].dayOfYear &&
                    it.dateTime.dayOfYear <= dates[dates.size - 1].dayOfYear
        }
    }

    // Получение расписания рациона на день
    fun getDayRation(day: LocalDate): List<Mealtime> {
        return generateMealtime().filter {
            it.dateTime.dayOfYear == day.dayOfYear &&
                    it.dateTime.year == day.year
        }
    }

    // Перевод номера дня недели в его название
    fun getDayOfWeekName(dayOfWeek: Int): Int {
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

    private fun generateMealtime(): List<Mealtime> =
        listOf(
            Mealtime(
                name = "Завтрак",
                caloric = 1500,
                receipt = "яичница, банан, чай",
                startTime = "10:00",
                endTime = "10:20",
                dateTime = LocalDate.now()
            ),
            Mealtime(
                name = "Обед",
                caloric = 1500,
                receipt = "яичница, банан, чай",
                startTime = "16:00",
                endTime = "16:20",
                dateTime = LocalDate.now()
            ),
            Mealtime(
                name = "Ужин",
                caloric = 1500,
                receipt = "яичница, банан, чай",
                startTime = "21:00",
                endTime = "21:20",
                dateTime = LocalDate.now()
            ),
            Mealtime(
                name = "Завтрак",
                caloric = 1500,
                receipt = "яичница, банан, чай",
                startTime = "10:00",
                endTime = "10:20",
                dateTime = LocalDate.now().plusDays(1)
            ),
            Mealtime(
                name = "Ужин",
                caloric = 1500,
                receipt = "яичница, банан, чай",
                startTime = "21:00",
                endTime = "21:20",
                dateTime = LocalDate.now().minusDays(1)
            )
        )
}