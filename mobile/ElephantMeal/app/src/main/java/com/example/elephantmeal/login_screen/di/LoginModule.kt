package com.example.elephantmeal.login_screen.di

import com.example.elephantmeal.login_screen.domain.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {

    @Provides
    fun provideLoginUseCase(): LoginUseCase {
        return LoginUseCase()
    }
}