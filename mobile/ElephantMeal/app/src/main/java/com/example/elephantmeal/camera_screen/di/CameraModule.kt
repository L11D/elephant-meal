package com.example.elephantmeal.camera_screen.di

import com.example.elephantmeal.camera_screen.domain.CameraUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CameraModule {

    @Provides
    fun provideCameraUseCase(): CameraUseCase {
        return CameraUseCase()
    }
}