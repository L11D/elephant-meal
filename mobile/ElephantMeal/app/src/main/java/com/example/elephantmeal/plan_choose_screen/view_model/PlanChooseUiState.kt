package com.example.elephantmeal.plan_choose_screen.view_model

import com.example.elephantmeal.plan_choose_screen.domain.MealPlan

data class PlanChooseUiState(
    val mealPlans: List<MealPlan> = listOf(),
    val selectedIndex: Int? = null,
    val isContinueButtonEnabled: Boolean = false
)
