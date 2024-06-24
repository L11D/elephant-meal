package com.example.elephantmeal.menu_screen.domain

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.withContext

class MenuUseCase {

    companion object {
        private val CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
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