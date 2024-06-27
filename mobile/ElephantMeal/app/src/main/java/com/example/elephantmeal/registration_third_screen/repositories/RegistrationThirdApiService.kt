package com.example.elephantmeal.registration_third_screen.repositories

import com.example.elephantmeal.registration_third_screen.domain.models.RegistrationData
import com.example.elephantmeal.registration_third_screen.domain.models.Token
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationThirdApiService {
    @POST("api/v1/user/register/")
    suspend fun register(
        @Body registrationData: RegistrationData
    ): Token
}