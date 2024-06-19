package com.example.elephantmeal.registration_second_screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.elephantmeal.common.presentation.BirthdayInputField
import com.example.elephantmeal.common.presentation.ElephantMealLogo
import com.example.elephantmeal.common.presentation.NumberInputField
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.registration_second_screen.view_model.Gender
import com.example.elephantmeal.registration_second_screen.view_model.RegistrationSecondViewModel
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.DeselectedGenderColor
import com.example.elephantmeal.ui.theme.GenderSelectionBackgroundColor
import com.example.elephantmeal.ui.theme.GrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor
import com.example.elephantmeal.ui.theme.SelectedGenderColor
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection

@Composable
fun RegistrationSecondScreen(
    onBackButtonClick: () -> Unit,
    onLoginButtonClick: () -> Unit,
    viewModel: RegistrationSecondViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Логотип приложения
        RegistrationSecondScreenLogo(
            onBackButtonClick = onBackButtonClick
        )

        // Заголовок экрана
        RegistrationSecondScreenHeader()

        // Выбор пола
        GenderSelection()

        // Выбор даты рождения
        SelectBirthday()

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
            text = stringResource(id = R.string.skip),
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
fun RegistrationSecondScreenLogo(
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
fun RegistrationSecondScreenHeader() {
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

// Выбор пола
@Composable
fun GenderSelection(
    viewModel: RegistrationSecondViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column {
        // Выбор пола
        Text(
            modifier = Modifier
                .padding(24.dp, 20.dp, 0.dp, 0.dp),
            text = stringResource(id = R.string.gender),
            style = TextStyle(
                fontSize = 14.sp,
                color = GrayColor
            )
        )

        val interactionSource = remember {
            MutableInteractionSource()
        }

        Row(
            modifier = Modifier
                .padding(24.dp, 12.dp, 24.dp, 0.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .height(42.dp)
                .background(GenderSelectionBackgroundColor)
        ) {
            // Кнопка Мужчина
            Box(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 0.dp, 2.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(7.dp))
                    .background(if (state.gender == Gender.Male) Color.White else Color.Transparent)
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        viewModel.onMaleSelected()
                    }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(id = R.string.male),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = if (state.gender == Gender.Male) SelectedGenderColor else DeselectedGenderColor
                    )
                )
            }

            // Кнопка Женщина
            Box(
                modifier = Modifier
                    .padding(0.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(7.dp))
                    .background(if (state.gender == Gender.Female) Color.White else Color.Transparent)
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        viewModel.onFemaleSelected()
                    }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(id = R.string.female),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = if (state.gender == Gender.Female) SelectedGenderColor else DeselectedGenderColor
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBirthday(
    viewModel: RegistrationSecondViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val calendarState = rememberSheetState()

    // Календарь
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true
        ),
        selection = CalendarSelection.Date(
            selectedDate = state.selectedDate,
            onSelectDate = {
                    date -> viewModel.selectBirthDate(date)
            }
        ),
    )

    BirthdayInputField(
        label = stringResource(id = R.string.birth_date),
        topPadding = 16.dp,
        value = viewModel.birthDate,
        onValueChange = { },
        onCalendarClick = {
            calendarState.show()
        }
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