package com.example.elephantmeal.products_ban_screen.view_model

sealed interface ProductsBanEvent {
    data object Continue: ProductsBanEvent
}