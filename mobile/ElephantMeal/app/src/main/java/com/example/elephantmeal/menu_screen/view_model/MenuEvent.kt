package com.example.elephantmeal.menu_screen.view_model

sealed interface MenuEvent {
    data object LaunchCamera: MenuEvent
}