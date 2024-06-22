package com.example.elephantmeal.products_ban_screen.view_model

import com.example.elephantmeal.products_ban_screen.domain.Category
import com.example.elephantmeal.products_ban_screen.domain.Subcategory

data class ProductsBanUiState(
    val categories: List<Category> = listOf(),
    val subcategories: List<Subcategory> = listOf()
)
