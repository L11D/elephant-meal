package com.example.elephantmeal.menu_screen.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.camera_screen.view_model.CameraViewModel
import com.example.elephantmeal.common.navigation.Screen
import com.example.elephantmeal.common.presentation.ElephantMealLogo
import com.example.elephantmeal.menu_screen.view_model.MenuEvent
import com.example.elephantmeal.menu_screen.view_model.MenuViewModel
import com.example.elephantmeal.day_screen.presentation.BottomNavBar

// Экран меню
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuScreen(
    onHomeClick: () -> Unit,
    onTodayClick: (Boolean) -> Unit,
    onDayClick: () -> Unit,
    onWeekClick: () -> Unit,
    isWeekSelected: Boolean,
    viewModel: MenuViewModel = hiltViewModel(),
    cameraViewModel: CameraViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    // Диалоговое окно выбора фото
    if (state.isPhotoChoosing){
        ChoosePhotoDialog(
            onDismissRequest = viewModel::onPhotoChooseDismiss,
            onTakePhoto = {
                viewModel.takePhoto(context)
            },
            onChoosePhoto = {
                viewModel.onPhotoChoose()
            }
        )
    }

    // Выбор фото из галереи
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                cameraViewModel.onPhotoChosen(uri)
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is MenuEvent.ChoosePhotoFromGallery -> {
                    galleryLauncher.launch("image/*")
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                isWeekModeSelected = isWeekSelected,
                currentScreen = Screen.MenuScreen,
                onTodayClick = onTodayClick,
                onWeekClick = onWeekClick
            )
        },
        topBar = {
            // Логотип приложения
            ElephantMealLogo()
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Настройки профиля пользователя
            ProfileSettings(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(
                        rememberScrollState()
                    )
            )

            // Тень от нижней навигационной панели
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.04f), Color.Transparent),
                            start = Offset(0.0f, Float.POSITIVE_INFINITY),
                            end = Offset(0.0f, 0.0f)
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.White, Color.Transparent),
                            start = Offset(0.0f, 0.0f),
                            end = Offset(0.0f, Float.POSITIVE_INFINITY)
                        )
                    )
            )
        }
    }
}