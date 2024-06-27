package com.example.elephantmeal.registration_third_screen.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationData(
    val surname: String,
    val name: String,
    val patronymic: String,
    val email: String,
    val sex: Int = 1,
    val weight: Float = 68.0f,
    val height: Float = 180.0f,
    val birthDate: String = "2000-01-01",
    val password: String
)
