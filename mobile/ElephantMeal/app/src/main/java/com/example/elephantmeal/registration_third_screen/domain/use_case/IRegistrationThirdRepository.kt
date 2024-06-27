package com.example.elephantmeal.registration_third_screen.domain.use_case

import com.example.elephantmeal.registration_third_screen.domain.models.RegistrationData

interface IRegistrationThirdRepository {
    suspend fun register(registrationData: RegistrationData): Boolean
}