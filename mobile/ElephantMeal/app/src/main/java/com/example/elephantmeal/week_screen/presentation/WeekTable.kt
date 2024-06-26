package com.example.elephantmeal.week_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.day_screen.domain.Mealtime
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.LightBlueColor
import com.example.elephantmeal.ui.theme.LightGrayColor
import com.example.elephantmeal.ui.theme.PaleBlueColor
import com.example.elephantmeal.week_screen.domain.WeekDate
import com.example.elephantmeal.week_screen.view_model.WeekViewModel

// Таблица расписания рациона на неделю
@Composable
fun WeekTable(
    viewModel: WeekViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Row(
        modifier = Modifier
            .padding(0.dp, 32.dp, 0.dp, 0.dp)
            .fillMaxSize()
    ) {
        // Первая колонка таблицы
        FirstColumn()

        state.week.forEach {
            DayOfWeekColumn(day = it)
        }

        Spacer(modifier = Modifier.width(24.dp))
    }
}

// Первая колонка таблицы
@Composable
fun FirstColumn(
    viewModel: WeekViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .padding(24.dp, 0.dp, 0.dp, 0.dp)
            .width(112.dp)
    ) {
        EmptyCorner()

        for (order in 1..state.maxMealtimes) {
            MealtimeOrder(order = order)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Пустая угловая клетка таблицы
@Composable
fun EmptyCorner() {
    Box(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
    )
}

// Номер приёма пищи
@Composable
fun MealtimeOrder(
    order: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
    ) {
        Text(
            modifier = Modifier
                .offset(8.dp)
                .align(Alignment.Center),
            text = order.toString() + stringResource(id = R.string.mealtime_order),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(1.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(LightGrayColor)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(1.dp)
                .background(LightGrayColor)
        )
    }
}

// Колонка с расписанием рациона на день
@Composable
fun DayOfWeekColumn(
    day: WeekDate,
    viewModel: WeekViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .width(144.dp)
    ) {
        // Дата в расписании
        DateTableHeader(day = day)

        // Расписание рациона на день
        var filledCount = 0

        state.weekRation.filter { it.dateTime.dayOfMonth == day.day }.forEach {
            MealtimeElement(it)
            filledCount++
        }

        for (i in filledCount..state.maxMealtimes - 1) {
            EmptyMealtime()
        }
    }
}

// Дата в расписании
@Composable
fun DateTableHeader(
    day: WeekDate
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp, 0.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(
                text = stringResource(id = day.dayOfWeek),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                modifier = Modifier
                    .padding(0.dp, 4.dp, 0.dp, 4.dp),
                text = "${day.day} " + stringResource(id = day.monthStringResource),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = DarkGrayColor
                )
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(1.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(2.dp))
                .background(LightGrayColor)
        )
    }
}

// Отображение приёма пищи
@Composable
fun MealtimeElement(
    mealtime: Mealtime,
    viewModel: WeekViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val interactionSource = remember {
        MutableInteractionSource()
    }

    // Диалог просмотра приёма пищи
    if (state.isMealtimeDialogVisible)
        MealtimeDialog()

    // Ячейка таблицы с приёмом пищи
    Box(
        modifier = Modifier
            .height(96.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp, 16.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(4.dp))
                .background(PaleBlueColor)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    viewModel.showMealtimeDialog(mealtime)
                }
        ) {
            Text(
                modifier = Modifier
                    .padding(12.dp, 10.dp, 12.dp, 0.dp),
                text = mealtime.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(12.dp, 0.dp, 12.dp, 10.dp),
                text = "${mealtime.startTime} - ${mealtime.endTime}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = DarkGrayColor
                )
            )
        }

        Box(
            modifier = Modifier
                .padding(8.dp, 16.dp)
                .width(5.dp)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topStart = 4.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 0.dp,
                        topEnd = 0.dp
                    )
                )
                .background(LightBlueColor)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(1.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(2.dp))
                .background(LightGrayColor)
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(1.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(2.dp))
                .background(LightGrayColor)
        )
    }
}

// Пустая ячейка
@Composable
fun EmptyMealtime() {
    Box(
        modifier = Modifier
            .height(96.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(1.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(2.dp))
                .background(LightGrayColor)
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(1.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(2.dp))
                .background(LightGrayColor)
        )
    }
}