package com.example.elephantmeal.products_ban_screen.domain

data class Category(
    val name: String,
    val isSelected: Boolean,
    val subcategories: List<Subcategory>
)
