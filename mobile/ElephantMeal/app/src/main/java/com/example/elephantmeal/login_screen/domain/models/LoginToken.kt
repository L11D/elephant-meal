package com.example.elephantmeal.login_screen.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginToken(
    val access_token: String,
    val token_type: String
)
