package com.example.elephantmeal.login_screen.repositories

import com.example.elephantmeal.common.authorization.AccessToken
import com.example.elephantmeal.login_screen.domain.models.LoginCredentials
import com.example.elephantmeal.login_screen.domain.use_case.ILoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val _loginApiService: LoginApiService
) : ILoginRepository {
    override suspend fun login(loginCredentials: LoginCredentials): Boolean {
        try {
            val token = _loginApiService.login(loginCredentials)
            AccessToken.token = token.access_token

            return true
        }
        catch (exception: Exception) {
            return false
        }
    }
}