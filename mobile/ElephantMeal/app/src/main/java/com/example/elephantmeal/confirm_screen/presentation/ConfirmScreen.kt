package com.example.elephantmeal.confirm_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.common.presentation.ElephantMealLogo
import com.example.elephantmeal.common.presentation.InputField
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.confirm_screen.view_model.ConfirmEvent
import com.example.elephantmeal.confirm_screen.view_model.ConfirmViewModel

// Подтверждение регистрации
@Composable
fun ConfirmScreen(
    onConfirm: () -> Unit,
    viewModel: ConfirmViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is ConfirmEvent.Confirmed -> {
                    onConfirm()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ElephantMealLogo()

        Text(
            modifier = Modifier
                .padding(24.dp, 24.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.confirm_registration),
            style = TextStyle(
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            bottomPadding = 32.dp,
            text = stringResource(id = R.string.confirm),
            onClick = {
                viewModel.confirm()
            }
        )
    }
}