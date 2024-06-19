package com.example.elephantmeal.registration_first_screen.domain

import android.util.Patterns

class RegistrationFirstUseCase {
    // Валидация email
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}