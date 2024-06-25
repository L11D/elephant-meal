package com.example.elephantmeal.menu_screen.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.camera_screen.view_model.CameraViewModel
import com.example.elephantmeal.common.presentation.ElephantMealLogo
import com.example.elephantmeal.common.presentation.GenderSelection
import com.example.elephantmeal.common.presentation.InputField
import com.example.elephantmeal.common.presentation.NumberInputField
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.common.presentation.SelectBirthday
import com.example.elephantmeal.menu_screen.view_model.MenuEvent
import com.example.elephantmeal.menu_screen.view_model.MenuViewModel
import kotlinx.coroutines.flow.collect

// Экран меню
@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    cameraViewModel: CameraViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val cameraState by cameraViewModel.state.collectAsState()

    val context = LocalContext.current

    // Диалоговое окно выбора фото
    if (state.isPhotoChoosing){
        ChoosePhotoDialog(
            onDismissRequest = viewModel::onPhotoChooseDismiss,
            onTakePhoto = {
                viewModel.takePhoto(context)
            },
            onChoosePhoto = {
                viewModel.onPhotoChoose()
            }
        )
    }

    // Выбор фото из галереи
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                cameraViewModel.onPhotoChosen(uri)
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is MenuEvent.ChoosePhotoFromGallery -> {
                    galleryLauncher.launch("image/*")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        // Логотип приложения
        ElephantMealLogo()

        // Выбор фото профиля пользователя
        Avatar()

        // Поле ввода фамилии
        InputField(
            label = stringResource(id = R.string.surname),
            topPadding = 24.dp,
            value = viewModel.surname,
            onValueChange = {
                viewModel.onSurnameChange(it)
            }
        )

        // Поле ввода имени
        InputField(
            label = stringResource(id = R.string.name),
            topPadding = 16.dp,
            value = viewModel.name,
            onValueChange = {
                viewModel.onNameChange(it)
            }
        )

        // Поле ввода отчества
        InputField(
            label = stringResource(id = R.string.last_name),
            topPadding = 16.dp,
            value = viewModel.lastName,
            onValueChange = {
                viewModel.onLastNameChange(it)
            }
        )

        // Выбор пола
        GenderSelection(
            topPadding = 32.dp,
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
                viewModel.onBirthDateSelected(date)
            },
            selectedDate = state.selectedBirthDate,
            birthDateFieldValue = viewModel.birthDate
        )

        // Поле ввода роста
        NumberInputField(
            label = stringResource(id = R.string.height),
            unit = stringResource(id = R.string.cm),
            topPadding = 16.dp,
            value = viewModel.height,
            onValueChange = {
                viewModel.onHeightChange(it)
            }
        )

        // Поле ввода веса
        NumberInputField(
            label = stringResource(id = R.string.weight),
            unit = stringResource(id = R.string.kg),
            topPadding = 16.dp,
            value = viewModel.weight,
            onValueChange = {
                viewModel.onWeightChange(it)
            }
        )

        // Кнопка сохранения профиля
        PrimaryButton(
            topPadding = 32.dp,
            bottomPadding = 32.dp,
            text = stringResource(id = R.string.save),
            isEnabled = state.isSaveActive || cameraState.isSaveActive,
            onClick = {
                viewModel.onSave()
            }
        )
    }
}