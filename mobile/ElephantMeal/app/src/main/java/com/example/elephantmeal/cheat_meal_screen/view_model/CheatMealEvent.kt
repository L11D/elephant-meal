package com.example.elephantmeal.cheat_meal_screen.view_model

sealed interface CheatMealEvent {
    data object Continue: CheatMealEvent
}