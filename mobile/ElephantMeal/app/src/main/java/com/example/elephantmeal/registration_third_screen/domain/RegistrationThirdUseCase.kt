package com.example.elephantmeal.registration_third_screen.domain

class RegistrationThirdUseCase {
    // Проверка совпадения паролей
    fun doPasswordsMatch(password: String, passwordConfirmation: String): Boolean {
        return password == passwordConfirmation
    }
}