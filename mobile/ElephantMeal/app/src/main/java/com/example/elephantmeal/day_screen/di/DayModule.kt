package com.example.elephantmeal.day_screen.di

import com.example.elephantmeal.day_screen.domain.DayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DayModule {

    @Provides
    fun provideTodayUseCase(): DayUseCase {
        return DayUseCase()
    }
}