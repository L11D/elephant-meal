package com.example.elephantmeal.registration_third_screen.repositories

import android.util.Log
import com.example.elephantmeal.registration_third_screen.domain.models.RegistrationData
import com.example.elephantmeal.registration_third_screen.domain.use_case.IRegistrationThirdRepository
import javax.inject.Inject

class RegistrationThirdRepositoryImpl @Inject constructor(
    private val _registrationThirdApiService: RegistrationThirdApiService
) : IRegistrationThirdRepository {
    // Регистрация
    override suspend fun register(registrationData: RegistrationData): Boolean {
        try {
            val token = _registrationThirdApiService.register(registrationData)

            return true
        }
        catch (exception: Exception) {
            return false
        }
    }
}