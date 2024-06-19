package com.example.elephantmeal.login_screen.view_model

data class LoginUiState(
    val isPasswordVisible: Boolean = false,
    val isLoginEnabled: Boolean = false,
    val isEmailValid: Boolean = true,
    val isLoggedIn: Boolean = false
)
