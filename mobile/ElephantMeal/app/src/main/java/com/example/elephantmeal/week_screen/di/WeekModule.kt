package com.example.elephantmeal.week_screen.di

import com.example.elephantmeal.week_screen.domain.WeekUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class WeekModule {

    @Provides
    fun provideWeekUseCase(): WeekUseCase {
        return WeekUseCase()
    }
}