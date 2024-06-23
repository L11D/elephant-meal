package com.example.elephantmeal.products_preferences_screen.view_model

sealed interface ProductsPreferencesEvent {
    data object Continue: ProductsPreferencesEvent
}