package com.example.elephantmeal.registration_third_screen.view_model

sealed interface RegistrationThirdEvent {
    data object IsRegistered: RegistrationThirdEvent
}