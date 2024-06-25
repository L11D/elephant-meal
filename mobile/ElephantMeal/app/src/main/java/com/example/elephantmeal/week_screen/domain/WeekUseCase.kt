@file:Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")

package com.example.elephantmeal.week_screen.domain

import com.example.elephantmeal.R
import com.example.elephantmeal.day_screen.domain.Mealtime
import java.time.LocalDate
import java.time.Month

class WeekUseCase {
    // Получение дней текущей недели
    fun getWeek(date: LocalDate): List<LocalDate> {
        val currentDate = date
        val startOfWeek = currentDate.minusDays(currentDate.dayOfWeek.value.toLong() - 1)
        val week: MutableList<LocalDate> = mutableListOf()

        for (i in 0..6) week.add(startOfWeek.plusDays(i.toLong()))

        return week
    }



    // Получение расписания рациона на неделю
    fun getWeekRation(dates: List<LocalDate>): List<Mealtime> {
        return generateMealtime().filter {
            it.dateTime.dayOfYear >= dates[0].dayOfYear &&
                    it.dateTime.dayOfYear <= dates[dates.size - 1].dayOfYear
        }
    }

    // Форматирование даты
    fun formatDate(date: LocalDate): WeekDate {
        val day = date.dayOfMonth
        val month = when (date.month) {
            Month.JANUARY -> R.string.of_january
            Month.FEBRUARY -> R.string.of_february
            Month.MARCH -> R.string.of_march
            Month.APRIL -> R.string.of_april
            Month.MAY -> R.string.of_may
            Month.JUNE -> R.string.of_june
            Month.JULY -> R.string.of_july
            Month.AUGUST -> R.string.of_august
            Month.SEPTEMBER -> R.string.of_september
            Month.OCTOBER -> R.string.of_october
            Month.NOVEMBER -> R.string.of_november
            Month.DECEMBER -> R.string.of_december
        }

        return WeekDate(
            day = day,
            monthStringResource = month
        )
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