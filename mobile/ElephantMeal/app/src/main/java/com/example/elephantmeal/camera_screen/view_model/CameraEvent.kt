package com.example.elephantmeal.camera_screen.view_model

sealed interface CameraEvent {
    data object CloseCamera: CameraEvent
}