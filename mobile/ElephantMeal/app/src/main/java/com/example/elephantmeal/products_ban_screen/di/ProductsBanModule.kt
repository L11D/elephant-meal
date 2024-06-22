package com.example.elephantmeal.products_ban_screen.di

import com.example.elephantmeal.products_ban_screen.domain.ProductsBanUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ProductsBanModule {

    @Provides
    fun provideProductsBanUseCase(): ProductsBanUseCase {
        return ProductsBanUseCase()
    }
}