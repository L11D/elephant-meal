package com.example.elephantmeal.common.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elephantmeal.R
import com.example.elephantmeal.registration_second_screen.view_model.Gender
import com.example.elephantmeal.ui.theme.DeselectedGenderColor
import com.example.elephantmeal.ui.theme.ErrorColor
import com.example.elephantmeal.ui.theme.GenderSelectionBackgroundColor
import com.example.elephantmeal.ui.theme.GrayColor
import com.example.elephantmeal.ui.theme.LightGrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor
import com.example.elephantmeal.ui.theme.SelectedGenderColor
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

// Поле ввода
@Composable
fun InputField(
    label: String,
    topPadding: Dp,
    value: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(8.dp, topPadding, 8.dp, 0.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            shape = RoundedCornerShape(8.dp),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    text = label,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                disabledBorderColor = Color.White,
                errorBorderColor = Color.White,
                focusedBorderColor = Color.White,
                focusedLabelColor = GrayColor,
                unfocusedLabelColor = GrayColor,
                disabledLabelColor = GrayColor
            ),
        )

        Box(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(
                    if (isError)
                        ErrorColor
                    else if (isFocused)
                        PrimaryColor
                    else
                        LightGrayColor
                )
                .align(Alignment.BottomCenter)
        )
    }
}

// Поле ввода пароля
@Composable
fun PasswordInputField(
    isVisible: Boolean,
    label: String,
    topPadding: Dp,
    value: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(8.dp, topPadding, 8.dp, 0.dp)
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                shape = RoundedCornerShape(8.dp),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                },
                visualTransformation = if (isVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White,
                    disabledBorderColor = Color.White,
                    errorBorderColor = Color.White,
                    focusedBorderColor = Color.White,
                    focusedLabelColor = GrayColor,
                    unfocusedLabelColor = GrayColor,
                    disabledLabelColor = GrayColor
                ),
            )

            Image(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 16.dp, 12.dp)
                    .align(Alignment.Bottom)
                    .clip(CircleShape)
                    .clickable {
                        onPasswordVisibilityChange()
                    },
                imageVector = ImageVector.vectorResource(
                    id = if (isVisible)
                        R.drawable.closed_eye
                    else
                        R.drawable.opened_eye
                ),
                contentDescription = stringResource(
                    id = if (isVisible)
                        R.string.closed_eye_description
                    else
                        R.string.opened_eye_description
                )
            )
        }


        Box(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(
                    if (isError)
                        ErrorColor
                    else if (isFocused)
                        PrimaryColor
                    else
                        LightGrayColor
                )
                .align(Alignment.BottomCenter)
        )
    }
}

// Поле ввода даты рождения
@Composable
fun BirthDateInputField(
    label: String,
    topPadding: Dp,
    value: String,
    onValueChange: (String) -> Unit,
    onCalendarClick: () -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(8.dp, topPadding, 8.dp, 0.dp)
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                enabled = false,
                shape = RoundedCornerShape(8.dp),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                maxLines = 1,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White,
                    disabledBorderColor = Color.White,
                    errorBorderColor = Color.White,
                    focusedBorderColor = Color.White,
                    focusedLabelColor = GrayColor,
                    unfocusedLabelColor = GrayColor,
                    disabledLabelColor = GrayColor
                ),
            )

            Image(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 16.dp, 12.dp)
                    .align(Alignment.Bottom)
                    .clip(CircleShape)
                    .clickable {
                        onCalendarClick()
                    },
                imageVector = ImageVector.vectorResource(R.drawable.calendar_icon),
                contentDescription = stringResource(R.string.calendar_description)
            )
        }


        Box(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(
                    if (isFocused) PrimaryColor else LightGrayColor
                )
                .align(Alignment.BottomCenter)
        )
    }
}

// Календарь
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBirthday(
    onBirthdateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate,
    birthDateFieldValue: String
) {
    val calendarState = rememberSheetState()

    // Календарь
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedDate,
            onSelectDate = {
                    date -> onBirthdateSelected(date)
            }
        ),
    )

    BirthDateInputField(
        label = stringResource(id = R.string.birth_date),
        topPadding = 16.dp,
        value = birthDateFieldValue,
        onValueChange = { },
        onCalendarClick = {
            calendarState.show()
        }
    )
}

// Поле ввода чисел
@Composable
fun NumberInputField(
    label: String,
    unit: String,
    topPadding: Dp,
    value: String,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(8.dp, topPadding, 0.dp, 0.dp)
            .width(144.dp)
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                shape = RoundedCornerShape(8.dp),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White,
                    disabledBorderColor = Color.White,
                    errorBorderColor = Color.White,
                    focusedBorderColor = Color.White,
                    focusedLabelColor = GrayColor,
                    unfocusedLabelColor = GrayColor,
                    disabledLabelColor = GrayColor
                ),
            )


            Text(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 24.dp, 20.dp)
                    .align(Alignment.Bottom),
                text = unit,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = GrayColor
                )
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(
                    if (isFocused) PrimaryColor else LightGrayColor
                )
                .align(Alignment.BottomCenter)
        )
    }
}

// Строка поиска
@Composable
fun SearchField(
    label: String,
    topPadding: Dp,
    value: String,
    onValueChange: (String) -> Unit,
    onValueClear: () -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(8.dp, topPadding, 8.dp, 0.dp)
    ) {
        Row {
            Image(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 0.dp, 12.dp)
                    .align(Alignment.Bottom)
                    .size(24.dp, 24.dp)
                    .clip(CircleShape),
                imageVector = ImageVector.vectorResource(id = R.drawable.search_glass),
                contentDescription = stringResource(id = R.string.search_glass_description)
            )

            OutlinedTextField(
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                shape = RoundedCornerShape(8.dp),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White,
                    disabledBorderColor = Color.White,
                    errorBorderColor = Color.White,
                    focusedBorderColor = Color.White,
                    focusedLabelColor = GrayColor,
                    unfocusedLabelColor = GrayColor,
                    disabledLabelColor = GrayColor
                ),
            )

            if (value.isNotEmpty()) {
                Image(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 16.dp, 12.dp)
                        .align(Alignment.Bottom)
                        .size(24.dp, 24.dp)
                        .clip(CircleShape)
                        .clickable {
                            onValueClear()
                        },
                    imageVector = ImageVector.vectorResource(id = R.drawable.cross_icon),
                    contentDescription = stringResource(id = R.string.cross_icon_description)
                )
            }

        }


        Box(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(
                    if (isFocused)
                        PrimaryColor
                    else
                        LightGrayColor
                )
                .align(Alignment.BottomCenter)
        )
    }
}


// Выбор пола
@Composable
fun GenderSelection(
    topPadding: Dp = 20.dp,
    selectedGender: Gender?,
    onMaleSelected: () -> Unit,
    onFemaleSelected: () -> Unit
) {
    Column {
        // Выбор пола
        Text(
            modifier = Modifier
                .padding(24.dp, topPadding, 0.dp, 0.dp),
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
                    .background(if (selectedGender == Gender.Male) Color.White else Color.Transparent)
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onMaleSelected()
                    }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(id = R.string.male),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = if (selectedGender == Gender.Male) SelectedGenderColor else DeselectedGenderColor
                    )
                )
            }

            // Кнопка Женщина
            Box(
                modifier = Modifier
                    .padding(0.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(7.dp))
                    .background(if (selectedGender == Gender.Female) Color.White else Color.Transparent)
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onFemaleSelected()
                    }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(id = R.string.female),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = if (selectedGender == Gender.Female) SelectedGenderColor else DeselectedGenderColor
                    )
                )
            }
        }
    }
}
