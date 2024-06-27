package com.example.elephantmeal.menu_screen.repositories

import android.util.Log
import com.example.elephantmeal.common.authorization.AccessToken
import com.example.elephantmeal.menu_screen.domain.models.UpdateProfile
import com.example.elephantmeal.menu_screen.domain.models.UserProfile
import com.example.elephantmeal.menu_screen.domain.use_case.IMenuRepository
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val _menuApiService: MenuApiService
) : IMenuRepository {
    override suspend fun getUserProfile(): UserProfile {
        val profile = _menuApiService.getUserProfile("Bearer ${AccessToken.token}")

        return profile
    }

    override suspend fun logout() {
        _menuApiService.logout("Bearer ${AccessToken.token}")
    }

    override suspend fun updateProfile(newProfile: UpdateProfile) {
        try {
            _menuApiService.updateProfile(newProfile, "Bearer ${AccessToken.token}")
        }
        catch (exception: Exception) {
            Log.d("exception", exception.message ?: "")
        }
    }
}