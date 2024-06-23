package com.example.elephantmeal.products_preferences_screen.view_model

import com.example.elephantmeal.products_ban_screen.domain.Category
import com.example.elephantmeal.products_ban_screen.domain.Product
import com.example.elephantmeal.products_ban_screen.domain.Subcategory

data class ProductsPreferencesUiState(
    val categories: List<Category> = listOf(),
    val subcategories: List<Subcategory> = listOf(),
    val hints: List<Product> = listOf(),
    val isSearchVisible: Boolean = false
)
