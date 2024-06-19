package com.example.elephantmeal.registration_second_screen.view_model

import java.time.LocalDate

data class RegistrationSecondUiState(
    val gender: Gender? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val height: Double = 180.0,
    val weight: Double = 68.0,
    val isFilled: Boolean = false
)
