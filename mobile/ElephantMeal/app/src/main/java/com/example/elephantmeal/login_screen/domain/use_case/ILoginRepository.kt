package com.example.elephantmeal.login_screen.domain.use_case

import com.example.elephantmeal.login_screen.domain.models.LoginCredentials

interface ILoginRepository {
    suspend fun login(loginCredentials: LoginCredentials): Boolean
}