package com.example.elephantmeal.today_screen.presentation

import android.graphics.LinearGradient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elephantmeal.R
import com.example.elephantmeal.common.presentation.ElephantMealLogo

// Экран просмотра расписания приёма пищи на день
@Composable
fun TodayScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Логотип приложения
        ElephantMealLogo()

        // Заголовок экрана
        Text(
            modifier = Modifier
                .padding(24.dp, 24.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.daily_meal_plan),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Выбор дня просмотра расписания рациона
        WeekRow()

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            // Расписание рациона на день
            DayTimetable()

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

        // Нижняя навигационная панель
        BottomNavBar(
            isWeekModeSelected = false
        )
    }
}