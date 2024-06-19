package com.example.elephantmeal.registration_first_screen.view_model

sealed interface RegistrationFirstEvent {
    data object Login: RegistrationFirstEvent
}