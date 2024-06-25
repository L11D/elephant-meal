package com.example.elephantmeal.week_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.week_screen.view_model.WeekViewModel

// Переключение недели
@Composable
fun WeeksSwitcher(
    viewModel: WeekViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Row(
        modifier = Modifier
            .padding(24.dp, 14.dp, 24.dp, 0.dp)
            .height(48.dp)
    ) {
        // Кнопка переключения на расписание предыдущей недели
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            imageVector = ImageVector.vectorResource(id = R.drawable.previous_week),
            contentDescription = stringResource(id = R.string.previous_week_description)
        )

        // Даты начала и конца недели
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            text = "${state.weekStart.day} " +
                    stringResource(id = state.weekStart.monthStringResource) +
                    " - ${state.weekEnd.day} " +
                    stringResource(id = state.weekEnd.monthStringResource),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Кнопка переключения на расписание следующей недели
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            imageVector = ImageVector.vectorResource(id = R.drawable.next_week),
            contentDescription = stringResource(id = R.string.nextx_week_description)
        )
    }
}