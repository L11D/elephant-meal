package com.example.elephantmeal.confirm_screen.view_model

sealed interface ConfirmEvent {
    data object Confirmed: ConfirmEvent
}