package com.example.elephantmeal.camera_screen.view_model

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elephantmeal.camera_screen.domain.CameraUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val _cameraUseCase: CameraUseCase
) : ViewModel() {
    private val _events = MutableSharedFlow<CameraEvent>()
    val events = _events.asSharedFlow()

    private val _state = MutableStateFlow(CameraUiState())
    val state = _state.asStateFlow()

    // Фотографирование
    fun takePhoto(
        controller: LifecycleCameraController,
        context: Context
    ) {
        val name = LocalDate.now().toString()
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/ElephantMeal")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        controller.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraX", "Taking photo is failed: ${exception.message}")
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    outputFileResults.savedUri?.let { uri ->
                        closeCamera()
                        setProfilePicture(uri)
                    }
                }
            }
        )
    }

    // Закрытие камеры
    fun closeCamera() {
        viewModelScope.launch {
            _events.emit(CameraEvent.CloseCamera)
        }
    }

    // Отображение фотографии в аватаре
    private fun setProfilePicture(uri: Uri) {
        _state.update { currentState ->
            currentState.copy(
                photoUri = uri,
                isSaveActive = true
            )
        }
    }

    // Удаление фото
    fun removePhoto() {
        _state.update { currentState ->
            currentState.copy(
                photoUri = null,
                isSaveActive = false
            )
        }
    }

    // Выбор фото из галереи
    fun onPhotoChosen(uri: Uri) {
        _state.update { currentState ->
            currentState.copy(
                photoUri = uri,
                isSaveActive = true
            )
        }
    }
}