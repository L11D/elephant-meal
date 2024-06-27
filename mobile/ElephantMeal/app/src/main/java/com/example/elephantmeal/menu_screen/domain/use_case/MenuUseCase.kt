package com.example.elephantmeal.menu_screen.domain.use_case

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.elephantmeal.menu_screen.domain.models.UserProfile
import javax.inject.Inject

class MenuUseCase @Inject constructor(
    private val _menuRepository: IMenuRepository
) {

    companion object {
        private val CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    suspend fun updateProfile(
        surname: String,
        name: String,
        lastName: String,
        gender: Int,
        birthDate: String,
        height: Float,
        weight: Float
    ) {
        _menuRepository
    }

    // Получение профиля пользователя
    suspend fun getUserProfile(): UserProfile {
        return _menuRepository.getUserProfile()
    }

    // Выход из аккаунта пользователя
    suspend fun logout() {
        _menuRepository.logout()
    }

    // Запрос разрешений
    fun requirePermissions(context: Context) {
        if (!hasPermissions(context)) {
            ActivityCompat.requestPermissions(
                context as Activity,
                CAMERA_PERMISSIONS,
                0
            )
        }
    }

    // Проверка наличия разрешений
    fun hasPermissions(context: Context): Boolean {
        return CAMERA_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}