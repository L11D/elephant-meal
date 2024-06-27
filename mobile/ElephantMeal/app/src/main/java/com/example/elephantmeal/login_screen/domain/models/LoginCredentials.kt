package com.example.elephantmeal.login_screen.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentials(
    val email: String,
    val password: String
)
