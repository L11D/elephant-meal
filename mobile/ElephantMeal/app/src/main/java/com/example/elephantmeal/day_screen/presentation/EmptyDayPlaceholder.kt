package com.example.elephantmeal.day_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elephantmeal.R
import com.example.elephantmeal.ui.theme.DarkGrayColor

@Composable
fun EmptyDayPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.empty_ration),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                modifier = Modifier
                    .padding(24.dp, 16.dp, 24.dp, 0.dp),
                text = stringResource(id = R.string.empty_plan),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = DarkGrayColor
                )
            )
        }
    }
}
