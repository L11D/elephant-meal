package com.example.elephantmeal.menu_screen.domain.use_case

import com.example.elephantmeal.menu_screen.domain.models.UserProfile

interface IMenuRepository {
    suspend fun getUserProfile(): UserProfile
    suspend fun logout()
}