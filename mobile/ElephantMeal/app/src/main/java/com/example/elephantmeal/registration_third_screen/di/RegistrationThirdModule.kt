package com.example.elephantmeal.registration_third_screen.di

import com.example.elephantmeal.registration_third_screen.domain.RegistrationThirdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RegistrationThirdModule {

    @Provides
    fun provideRegistrationThirdUseCase(): RegistrationThirdUseCase {
        return RegistrationThirdUseCase()
    }
}