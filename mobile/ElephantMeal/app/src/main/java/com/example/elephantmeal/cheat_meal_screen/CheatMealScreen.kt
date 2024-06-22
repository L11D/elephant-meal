package com.example.elephantmeal.cheat_meal_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.cheat_meal_screen.domain.DayOfWeek
import com.example.elephantmeal.cheat_meal_screen.view_model.CheatMealEvent
import com.example.elephantmeal.cheat_meal_screen.view_model.CheatMealViewModel
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.registration_second_screen.presentation.LogoWithBackButton
import com.example.elephantmeal.ui.theme.DarkBlueColor
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.LightGrayColor

// Экран настройки чит мила
@Composable
fun CheatMealScreen(
    onBackButtonClick: () -> Unit,
    onContinue: () -> Unit,
    viewModel: CheatMealViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is CheatMealEvent.Continue -> {
                    onContinue()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Логотип приложения
        LogoWithBackButton(
            onBackButtonClick = onBackButtonClick
        )

        // Заголовок экрана
        Text(
            modifier = Modifier
                .padding(24.dp, 62.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.cheat_meal_header),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Выбор дней
        DaysChoosing()

        // Настройка кол-ва чит мила
        CheatMealAmount()

        Spacer(modifier = Modifier.weight(1f))
        
        // Кнопка продолжить 
        PrimaryButton(
            bottomPadding = 32.dp,
            text = stringResource(id = R.string.continuation),
            onClick = {
                viewModel.onContinueButtonClick()
            }
        )
    }
}

// Выбор дней
@Composable
fun DaysChoosing(
    viewModel: CheatMealViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column {
        Text(
            modifier = Modifier
                .padding(24.dp, 32.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.choose_days),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Text(
            modifier = Modifier
                .padding(24.dp, 12.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.choose_days_description),
            lineHeight = 24.sp,
            style = TextStyle(
                fontSize = 16.sp,
                color = DarkGrayColor
            )
        )

        Row(
            modifier = Modifier
                .padding(24.dp, 16.dp, 24.dp, 0.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DayOfWeek.entries.forEach { dayOfWeek ->
                DayOfWeekElement(
                    dayOfWeek = dayOfWeek,
                    isSelected = dayOfWeek in state.selectedDaysOfWeek,
                    onClick = {
                        viewModel.onDayOfWeekSelected(dayOfWeek)
                    }
                )
            }
        }

    }
}

// День недели
@Composable
fun DayOfWeekElement(
    dayOfWeek: DayOfWeek,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .size(42.dp, 42.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = if (isSelected)
                    DarkBlueColor
                else
                    DarkGrayColor
            )
            .background(
                if (isSelected)
                    DarkBlueColor
                else
                    Color.White
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = when (dayOfWeek) {
                DayOfWeek.Monday -> stringResource(id = R.string.monday)
                DayOfWeek.Tuesday -> stringResource(id = R.string.tuesday)
                DayOfWeek.Wednesday -> stringResource(id = R.string.wednesday)
                DayOfWeek.Thursday -> stringResource(id = R.string.thursday)
                DayOfWeek.Friday -> stringResource(id = R.string.friday)
                DayOfWeek.Saturday -> stringResource(id = R.string.saturday)
                DayOfWeek.Sunday -> stringResource(id = R.string.sunday)
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected)
                    Color.White
                else
                    DarkGrayColor
            )
        )
    }
}

// Настройка кол-ва чит мила
@Composable
fun CheatMealAmount(
    viewModel: CheatMealViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column {
        Text(
            modifier = Modifier
                .padding(24.dp, 24.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.cheat_meal_amount),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Text(
            modifier = Modifier
                .padding(24.dp, 16.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.cheat_meal_amount_description),
            lineHeight = 24.sp,
            style = TextStyle(
                fontSize = 16.sp,
                color = DarkGrayColor
            )
        )

        var isDragging by remember {
            mutableStateOf(false)
        }

        Box(
            modifier = Modifier
                .height(56.dp)
                .padding(12.dp, 24.dp, 12.dp, 0.dp)
        ) {
            // Крайние значения ползунка
            Row(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .padding(20.dp, 0.dp)
                        .align(Alignment.Bottom),
                    text = "${state.minCheatMeal} " + stringResource(id = R.string.kcal),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = DarkGrayColor
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier = Modifier
                        .padding(20.dp, 0.dp)
                        .align(Alignment.Bottom),
                    text = "${state.maxCheatMeal} " + stringResource(id = R.string.kcal),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = DarkGrayColor
                    )
                )
            }

            // Индикатор текущего значения ползунка
            if (isDragging) {
                CheatMealSliderIndicator(
                    sliderValue = viewModel.cheatMealAmount,
                    kcal = state.cheatMealAmount
                )
            }
        }

        // Ползунок
        Slider(
            modifier = Modifier
                .padding(24.dp, 0.dp, 24.dp, 0.dp),
            value = viewModel.cheatMealAmount,
            onValueChange = {
                viewModel.onCheatMealChange(it)
                isDragging = true
            },
            colors = SliderDefaults.colors(
                activeTrackColor = DarkBlueColor,
                inactiveTrackColor = DarkGrayColor,
                thumbColor = DarkBlueColor
            ),
            onValueChangeFinished = {
                isDragging = false
            }
        )
    }
}

// Индикатор ползунка
@Composable
fun CheatMealSliderIndicator(
    sliderValue: Float,
    kcal: Int
) {
    Column {
        Column(
            modifier = Modifier
                .padding(12.dp, 0.dp, 24.dp, 0.dp)
                .fillMaxWidth(sliderValue)
        ) {
        }

        Box(
            modifier = Modifier
                .size(42.dp, 32.dp)
                .align(Alignment.End)
                .clip(RoundedCornerShape(8.dp))
                .background(LightGrayColor)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = kcal.toString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = DarkBlueColor
                )
            )
        }
    }
}