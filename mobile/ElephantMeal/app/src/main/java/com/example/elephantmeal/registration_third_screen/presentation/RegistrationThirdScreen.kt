package com.example.elephantmeal.registration_third_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.common.presentation.PasswordInputField
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.registration_second_screen.presentation.LogoWithBackButton
import com.example.elephantmeal.registration_second_screen.presentation.NavigateToLogin
import com.example.elephantmeal.registration_second_screen.presentation.RegistrationScreenHeader
import com.example.elephantmeal.registration_third_screen.view_model.RegistrationThirdEvent
import com.example.elephantmeal.registration_third_screen.view_model.RegistrationThirdViewModel
import com.example.elephantmeal.ui.theme.ErrorColor

// Третий экран регистрации
@Composable
fun RegistrationThirdScreen(
    onBackButtonClick: () -> Unit,
    onLoginButtonClick: () -> Unit,
    onRegistered: () -> Unit,
    viewModel: RegistrationThirdViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is RegistrationThirdEvent.IsRegistered -> {
                    onRegistered()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Лотогип приложения
        LogoWithBackButton(
            onBackButtonClick = onBackButtonClick
        )

        // Заголовок экрана
        RegistrationScreenHeader()

        // Поле ввода пароля
        PasswordInputField(
            isVisible = state.isPasswordVisible,
            label = stringResource(id = R.string.password),
            topPadding = 48.dp,
            value = viewModel.password,
            onValueChange = {
                viewModel.onPasswordChange(it)
            },
            onPasswordVisibilityChange = {
                viewModel.onPasswordVisibilityChange()
            }
        )

        // Поле подтверждения пароля
        PasswordInputField(
            isVisible = state.isPasswordVisible,
            label = stringResource(id = R.string.repeat_password),
            topPadding = 16.dp,
            value = viewModel.passwordConfirmation,
            isError = !state.arePasswordsValid,
            onValueChange = {
                viewModel.onPasswordConfirmationChange(it)
            },
            onPasswordVisibilityChange = {
                viewModel.onPasswordVisibilityChange()
            }
        )

        // Ошибка несовпадения пароля и его подтверждения
        if (!state.arePasswordsValid) {
            Text(
                modifier = Modifier
                    .padding(24.dp, 8.dp, 0.dp, 0.dp),
                text = stringResource(id = R.string.passwords_not_match),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = ErrorColor
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка Зарегистрироваться
        PrimaryButton(
            topPadding = 0.dp,
            text = stringResource(id = R.string.register),
            isEnabled = state.isRegisterButtonEnabled,
            onClick = {
                viewModel.onRegister()
            }
        )

        // Авторизация
        NavigateToLogin(
            onLoginButtonClick = onLoginButtonClick
        )
    }
}