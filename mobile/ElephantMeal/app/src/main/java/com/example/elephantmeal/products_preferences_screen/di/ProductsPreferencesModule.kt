package com.example.elephantmeal.products_preferences_screen.di

import com.example.elephantmeal.products_preferences_screen.domain.ProductsPreferencesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ProductsPreferencesModule {

    @Provides
    fun provideProductsPreferencesUseCase(): ProductsPreferencesUseCase {
        return ProductsPreferencesUseCase()
    }
}