package com.example.elephantmeal.week_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.elephantmeal.common.navigation.Screen
import com.example.elephantmeal.common.presentation.ElephantMealLogo
import com.example.elephantmeal.day_screen.presentation.BottomNavBar

// Экран просмотра расписания рациона на неделю
@Composable
fun WeekScreen(
    onHomeClick: () -> Unit,
    onDayClick: () -> Unit,
    onMenuClick: (isWeekModeSelected: Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Логотип приложения
        ElephantMealLogo()

        // Переключение недели
        WeeksSwitcher()

        // Расписание рациона на неделю
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
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

            // Тень от выбора недели
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.06f), Color.Transparent),
                            start = Offset(0.0f, 0.0f),
                            end = Offset(0.0f, Float.POSITIVE_INFINITY)
                        )
                    )
            )
        }

        // Нижняя навигационная пеналь
        BottomNavBar(
            isWeekModeSelected = true,
            currentScreen = Screen.TodayScreen,
            onDayClick = onDayClick,
            onMenuClick = onMenuClick
        )
    }
}