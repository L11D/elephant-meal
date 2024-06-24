package com.example.elephantmeal.camera_screen.domain

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class CameraUseCase {
    // Получение полного пути к файлу
    private fun getFullPath(context: Context, contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri, proj, null, null, null) ?: return null

        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val fullPath = cursor.getString(columnIndex)
        cursor.close()

        return fullPath
    }
}