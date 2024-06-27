package com.example.elephantmeal.login_screen.repositories

import com.example.elephantmeal.login_screen.domain.models.LoginCredentials
import com.example.elephantmeal.login_screen.domain.models.LoginToken
import com.example.elephantmeal.registration_third_screen.domain.models.Token
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("api/v1/user/login/")
    suspend fun login(
        @Body loginCredentials: LoginCredentials
    ): LoginToken
}