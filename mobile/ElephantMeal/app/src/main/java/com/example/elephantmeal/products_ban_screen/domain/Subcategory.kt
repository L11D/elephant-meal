package com.example.elephantmeal.products_ban_screen.domain

data class Subcategory(
    val name: String,
    val isSelected: Boolean,
    val products: List<Product>
)
