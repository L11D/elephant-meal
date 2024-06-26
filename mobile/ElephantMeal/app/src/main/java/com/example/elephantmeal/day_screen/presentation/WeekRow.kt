package com.example.elephantmeal.day_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.day_screen.view_model.DayViewModel
import com.example.elephantmeal.ui.theme.DayOfWeekGrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor
import com.example.elephantmeal.ui.theme.PaleBlueColor
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Текущая неделя
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekRow(
    viewModel: DayViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val calendarState = rememberSheetState()

    // Календарь
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date(
            selectedDate = state.selectedDate,
            onSelectDate = { date ->
                viewModel.selectDate(date)
            }
        ),
    )

    // Строка с днями выбранной недели
    Row(
        modifier = Modifier
            .padding(16.dp, 24.dp, 16.dp, 0.dp)
            .fillMaxWidth()
    ) {
        for (date in state.selectedWeek) {
            val isSelected = state.selectedDate == date

            Row(modifier = Modifier
                .weight(1f)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(
                    color = if (isSelected) PaleBlueColor else Color.Transparent
                )
                .clickable(
                    enabled = !isSelected,
                    onClick = {
                        viewModel.selectDate(date)
                    }
                ),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DayOfWeek(date)
            }
        }

        // Выбор другого дня
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(4.dp, 0.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable {
                    calendarState.show()
                },
            imageVector = ImageVector.vectorResource(R.drawable.options_icon),
            contentDescription = null
        )

    }
}

// День недели
@Composable
fun DayOfWeek(
    date: LocalDate,
    viewModel: DayViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd")
    val day = date.format(dateTimeFormatter)
    val isSelected = state.selectedDate == date

    Column(
        modifier = Modifier
            .background(
                color = if (isSelected) PaleBlueColor else Color.Transparent
            )
    ) {
        // Дата
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 8.dp, 0.dp, 0.dp),
            text = day.toString(),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) PrimaryColor else Color.Black
            )
        )

        // День недели
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = viewModel.getDayOfWeekName(date.dayOfWeek.value)),
            style = TextStyle(
                fontSize = 16.sp,
                color = if (isSelected) PrimaryColor else DayOfWeekGrayColor
            )
        )

        // Точка под выбранным днём
        val bottomPadding = if (isSelected) 10.dp else 16.dp

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 6.dp, 0.dp, bottomPadding),
        ) {
            if (isSelected)
                Image(
                    painter = painterResource(id = R.drawable.dot),
                    contentDescription = null
                )
        }
    }
}