package com.example.elephantmeal.registration_third_screen.domain.use_case

import com.example.elephantmeal.registration_third_screen.domain.models.RegistrationData
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class RegistrationThirdUseCase @Inject constructor(
    private val _registrationThirdRepository : IRegistrationThirdRepository
) {
    // Проверка совпадения паролей
    fun doPasswordsMatch(password: String, passwordConfirmation: String): Boolean {
        return password == passwordConfirmation
    }

    // Регистрация
    suspend fun register(
        surname: String,
        name: String,
        lastName: String,
        email: String,
        gender: Int?,
        weight: Float?,
        height: Float?,
        birthDate: String?,
        password: String
    ): Boolean {
        val inputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = inputDateFormat.parse(birthDate ?: "01.01.2000")
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDate = outputDateFormat.format(date)

        val registrationData = RegistrationData(
            surname = surname,
            name = name,
            patronymic = lastName,
            email = email,
            sex = gender ?: 1,
            weight = weight ?: 68.0f,
            height = height ?: 180.0f,
            birthDate = outputDate,
            password = password
        )

        return _registrationThirdRepository.register(registrationData)
    }
}