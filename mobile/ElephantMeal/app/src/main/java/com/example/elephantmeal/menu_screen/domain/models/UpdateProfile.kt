package com.example.elephantmeal.menu_screen.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfile(
    val surname: String,
    val name: String,
    val patronymic: String,
    val email: String,
    val sex: Int,
    val weight: Float,
    val height: Float,
    val birth_date: String
)
