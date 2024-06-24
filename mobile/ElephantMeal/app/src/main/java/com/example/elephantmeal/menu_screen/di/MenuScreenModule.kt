package com.example.elephantmeal.menu_screen.di

import com.example.elephantmeal.menu_screen.domain.MenuUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MenuScreenModule {

    @Provides
    fun provideMenuUseCase(): MenuUseCase {
        return MenuUseCase()
    }
}