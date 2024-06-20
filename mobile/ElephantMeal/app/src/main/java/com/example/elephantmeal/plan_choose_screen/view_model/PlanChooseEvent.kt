package com.example.elephantmeal.plan_choose_screen.view_model

sealed interface PlanChooseEvent {
    data object Continue: PlanChooseEvent
}