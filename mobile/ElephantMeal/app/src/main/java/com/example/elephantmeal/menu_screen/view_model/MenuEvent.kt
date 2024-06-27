package com.example.elephantmeal.menu_screen.view_model

sealed interface MenuEvent {
    data object ChoosePhotoFromGallery: MenuEvent
    data object Logout: MenuEvent
}