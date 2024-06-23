package com.example.elephantmeal.today_screen.di

import com.example.elephantmeal.today_screen.domain.TodayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class TodayModule {

    @Provides
    fun provideTodayUseCase(): TodayUseCase {
        return TodayUseCase()
    }
}