package com.example.elephantmeal.registration_first_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.common.presentation.ElephantMealLogo
import com.example.elephantmeal.common.presentation.InputField
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.registration_first_screen.view_model.RegistrationFirstEvent
import com.example.elephantmeal.registration_first_screen.view_model.RegistrationFirstViewModel
import com.example.elephantmeal.ui.theme.ErrorColor
import com.example.elephantmeal.ui.theme.GrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor
import kotlinx.coroutines.flow.collect

// Превый экран регстрации
@Composable
fun RegistrationFirstScreen(
    onBackButtonClick: () -> Unit,
    onLoginButtonClick: () -> Unit,
    onContinueButtonClick: (String, String, String, String) -> Unit,
    viewModel: RegistrationFirstViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is RegistrationFirstEvent.Login -> {
                    onContinueButtonClick(
                        viewModel.surname,
                        viewModel.name,
                        viewModel.lastName,
                        viewModel.email
                    )
                }
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box {
            // Логотип приложения
            ElephantMealLogo()

            // Кнопка возврата
            Image(
                modifier = Modifier
                    .padding(24.dp, 48.dp, 0.dp, 0.dp)
                    .clip(CircleShape)
                    .clickable {
                        onBackButtonClick()
                    },
                imageVector = ImageVector.vectorResource(id = R.drawable.back_arrow),
                contentDescription = stringResource(id = R.string.back_button)
            )
        }

        // Заголовок экрана
        Text(
            modifier = Modifier
                .padding(24.dp, 58.dp, 0.dp, 0.dp),
            text = stringResource(id = R.string.registration),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Поле ввода фамилии
        InputField(
            label = stringResource(id = R.string.surname),
            topPadding = 48.dp,
            value = viewModel.surname,
            onValueChange = { viewModel.editSurname(it) }
        )

        // Поле ввода имени
        InputField(
            label = stringResource(id = R.string.name),
            topPadding = 16.dp,
            value = viewModel.name,
            onValueChange = { viewModel.editName(it) }
        )

        // Поле ввода отчества
        InputField(
            label = stringResource(id = R.string.last_name),
            topPadding = 16.dp,
            value = viewModel.lastName,
            onValueChange = { viewModel.editLastName(it) }
        )

        // Поле ввода email
        InputField(
            label = stringResource(id = R.string.email),
            topPadding = 16.dp,
            value = viewModel.email,
            isError = !state.isEmailValid,
            onValueChange = { viewModel.editEmail(it) }
        )

        // Ошибка ввода некорректного email
        if (!state.isEmailValid) {
            Text(
                modifier = Modifier
                    .padding(24.dp, 8.dp, 0.dp, 0.dp),
                text = stringResource(id = R.string.invalid_email),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = ErrorColor
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка продолжить
        PrimaryButton(
            topPadding = 0.dp,
            isEnabled = state.isContinueEnabled,
            text = stringResource(id = R.string.continuation),
            onClick = { viewModel.onContinueButtonClick() }
        )

        // Авторизация
        val interactionSource = remember {
            MutableInteractionSource()
        }

        Row(
            modifier = Modifier
                .padding(0.dp, 20.dp, 0.dp, 32.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.already_have_an_account) + " ",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = GrayColor
                )
            )

            Text(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onLoginButtonClick()
                    },
                text = stringResource(id = R.string.sign_in),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = PrimaryColor
                )
            )
        }
    }
}