package com.example.elephantmeal.menu_screen.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.camera_screen.presentation.CameraScreen
import com.example.elephantmeal.menu_screen.view_model.MenuViewModel

@Composable
fun MenuWithCamera(
    menuViewModel: MenuViewModel = hiltViewModel(),
    onHomeClick: () -> Unit,
    onTodayClick: (Boolean) -> Unit,
    onDayClick: () -> Unit,
    onWeekClick: () -> Unit,
    isWeekSelected: Boolean
) {
    val state by menuViewModel.state.collectAsState()

    Box(
        modifier = Modifier
        .fillMaxSize()
    ) {
        if (state.isCameraEnabled) {
            CameraScreen(
                onCameraClosed = {
                    menuViewModel.onCameraClosed()
                }
            )
        }
        else {
            MenuScreen(
                onHomeClick = onHomeClick,
                onTodayClick = onTodayClick,
                onDayClick = onDayClick,
                onWeekClick = onWeekClick,
                isWeekSelected = isWeekSelected
            )
        }
    }
}