package com.example.elephantmeal.menu_screen.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String,
    val surname: String,
    val name: String,
    val patronymic: String,
    val email: String,
    val sex: Int?,
    val weight: Float?,
    val height: Float?,
    val birthdate: String?,
    val registration_date: String
)
