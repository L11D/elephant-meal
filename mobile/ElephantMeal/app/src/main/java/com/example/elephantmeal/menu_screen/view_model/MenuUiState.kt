package com.example.elephantmeal.menu_screen.view_model

import com.example.elephantmeal.registration_second_screen.view_model.Gender
import java.time.LocalDate

data class MenuUiState(
    val gender: Gender? = null,
    val selectedBirthDate: LocalDate = LocalDate.now(),
    val isSaveActive: Boolean = false,
    val isCameraEnabled: Boolean = false
)
