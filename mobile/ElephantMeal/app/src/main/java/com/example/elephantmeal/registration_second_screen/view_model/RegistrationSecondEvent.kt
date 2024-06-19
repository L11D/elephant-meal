package com.example.elephantmeal.registration_second_screen.view_model

sealed interface RegistrationSecondEvent {
    data object Continue: RegistrationSecondEvent
}