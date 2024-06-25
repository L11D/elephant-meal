package com.example.elephantmeal.day_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.day_screen.domain.Mealtime
import com.example.elephantmeal.day_screen.view_model.DayViewModel
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.LightBlueColor
import com.example.elephantmeal.ui.theme.LightGrayColor

// Расписание рациона на день
@Composable
fun DayTimetable(
    viewModel: DayViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        state.dayRation.forEach {
            DayTimetableElement(mealtime = it)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Ячейка расписания
@Composable
fun DayTimetableElement(
    mealtime: Mealtime
) {
    Column(
        modifier = Modifier
            .padding(24.dp, 16.dp, 24.dp, 0.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = LightGrayColor
            )
    ) {
        // Название приёма пищи
        Text(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
            text = mealtime.name,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Калорийность пищи
        Row(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.caloric_icon), 
                contentDescription = stringResource(id = R.string.caloric_icon_description)
            )

            Text(
                modifier = Modifier
                    .padding(12.dp, 0.dp),
                text = mealtime.caloric.toString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = DarkGrayColor
                )
            )
        }

        // Рецепт
        Row(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.paper_icon),
                contentDescription = stringResource(id = R.string.paper_icon_description)
            )

            Text(
                modifier = Modifier
                    .padding(12.dp, 0.dp),
                text = mealtime.receipt,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = DarkGrayColor
                )
            )
        }

        // Время приёма пищи
        Text(
            modifier = Modifier
                .padding(16.dp, 24.dp, 16.dp, 16.dp),
            text = "${mealtime.startTime} - ${mealtime.endTime}",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = LightBlueColor
            )
        )
    }
}