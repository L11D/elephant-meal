package com.example.elephantmeal.registration_first_screen.di

import com.example.elephantmeal.registration_first_screen.domain.RegistrationFirstUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RegistrationFirstModule {

    @Provides
    fun provideRegistrationFirstUseCase(): RegistrationFirstUseCase {
        return RegistrationFirstUseCase()
    }
}