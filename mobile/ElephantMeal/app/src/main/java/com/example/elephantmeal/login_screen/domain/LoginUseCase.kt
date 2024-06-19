package com.example.elephantmeal.login_screen.domain

import android.util.Patterns

class LoginUseCase {
    // Валидация email
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}