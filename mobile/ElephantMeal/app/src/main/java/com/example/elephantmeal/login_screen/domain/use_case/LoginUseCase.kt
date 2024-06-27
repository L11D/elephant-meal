package com.example.elephantmeal.login_screen.domain.use_case

import android.util.Patterns
import com.example.elephantmeal.login_screen.domain.models.LoginCredentials
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val _loginRepository: ILoginRepository
) {
    // Валидация email
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Авторизация
    suspend fun login(credentials: LoginCredentials): Boolean {
        return _loginRepository.login(credentials)
    }
}