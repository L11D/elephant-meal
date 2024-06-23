package com.example.elephantmeal.registration_second_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.elephantmeal.common.presentation.BirthDateInputField
import com.example.elephantmeal.common.presentation.ElephantMealLogo
import com.example.elephantmeal.common.presentation.GenderSelection
import com.example.elephantmeal.common.presentation.NumberInputField
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.common.presentation.SelectBirthday
import com.example.elephantmeal.registration_second_screen.view_model.RegistrationSecondEvent
import com.example.elephantmeal.registration_second_screen.view_model.RegistrationSecondViewModel
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.GrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@Composable
fun RegistrationSecondScreen(
    onBackButtonClick: () -> Unit,
    onLoginButtonClick: () -> Unit,
    onContinueButtonClick: () -> Unit,
    viewModel: RegistrationSecondViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is RegistrationSecondEvent.Continue -> {
                    onContinueButtonClick()
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
        RegistrationScreenHeader()

        // Выбор пола
        GenderSelection(
            selectedGender = state.gender,
            onMaleSelected = {
                viewModel.onMaleSelected()
            },
            onFemaleSelected = {
                viewModel.onFemaleSelected()
            }
        )

        // Выбор даты рождения
        SelectBirthday(
            onBirthdateSelected = { date ->
                viewModel.selectBirthDate(date)
            },
            selectedDate = state.selectedDate,
            birthDateFieldValue = viewModel.birthDate
        )

        // Поле ввода роста
        NumberInputField(
            label = stringResource(id = R.string.height), 
            topPadding = 16.dp,
            value = viewModel.height,
            unit = stringResource(id = R.string.cm),
            onValueChange = {
                viewModel.onHeightChange(it)
            }
        )

        // Поле ввода веса
        NumberInputField(
            label = stringResource(id = R.string.weight),
            topPadding = 16.dp,
            value = viewModel.weight,
            unit = stringResource(id = R.string.kg),
            onValueChange = {
                viewModel.onWeightChange(it)
            }
        )

        // Предупреждение пользователя
        Text(
            modifier = Modifier
                .padding(24.dp, 20.dp, 24.dp, 16.dp),
            text = stringResource(id = R.string.optional_warning),
            lineHeight = 24.sp,
            style = TextStyle(
                fontSize = 16.sp,
                color = DarkGrayColor
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка продолжить
        PrimaryButton(
            topPadding = 0.dp,
            text = stringResource(id = if (state.isFilled) R.string.continuation else R.string.skip),
            onClick = {
                viewModel.onContinueButtonClick()
            }
        )

        // Авторизация
        NavigateToLogin(onLoginButtonClick = onLoginButtonClick)
    }
}

// Логотип приложения
@Composable
fun LogoWithBackButton(
    onBackButtonClick: () -> Unit
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
}

// Заголовок экрана
@Composable
fun RegistrationScreenHeader() {
    Text(
        modifier = Modifier
            .padding(24.dp, 58.dp, 0.dp, 0.dp),
        text = stringResource(id = R.string.registration),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}

// Авторизация
@Composable
fun NavigateToLogin(
    onLoginButtonClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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