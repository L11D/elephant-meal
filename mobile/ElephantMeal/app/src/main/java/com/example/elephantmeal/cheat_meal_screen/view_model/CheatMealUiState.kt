package com.example.elephantmeal.cheat_meal_screen.view_model

import com.example.elephantmeal.cheat_meal_screen.domain.DayOfWeek

data class CheatMealUiState(
    val selectedDaysOfWeek: List<DayOfWeek> = listOf(),
    val cheatMealAmount: Int = 0,
    val minCheatMeal: Int = 0,
    val maxCheatMeal: Int = 1000
)
