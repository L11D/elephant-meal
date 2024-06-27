package com.example.elephantmeal.menu_screen.view_model

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elephantmeal.menu_screen.domain.use_case.MenuUseCase
import com.example.elephantmeal.registration_second_screen.view_model.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val _menuUseCase: MenuUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MenuUiState())
    val state = _state.asStateFlow()

    private var email = ""

    private val _events = MutableSharedFlow<MenuEvent>()
    val events = _events.asSharedFlow()

    var birthDate by mutableStateOf("")
        private set

    var surname by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    var height by mutableStateOf("")
        private set

    var weight by mutableStateOf("")
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = _menuUseCase.getUserProfile()
            surname = profile.surname
            name = profile.name
            lastName = profile.patronymic
            height = (profile.height ?: 68.0f).toString()
            weight = (profile.weight ?: 180.0f).toString()

            _state.update { currentState ->
                currentState.copy(
                    gender = if (profile.sex == 2) Gender.Female else Gender.Male
                )
            }

            val dateStr = profile.birthdate ?: "2000-01-01"
            val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
            val formattedDateStr = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            email = profile.email

            birthDate = formattedDateStr
        }
    }

    // Сделать фото
    fun takePhoto(context: Context) {
        _menuUseCase.requirePermissions(context)

        _state.update { currentState ->
            currentState.copy(
                isCameraEnabled = true,
                isPhotoChoosing = false
            )
        }
    }

    // Закрытие камеры
    fun onCameraClosed() {
        _state.update { currentState ->
            currentState.copy(
                isCameraEnabled = false
            )
        }
    }

    // Выбор фото
    fun onSetAvatar() {
        _state.update { currentState ->
            currentState.copy(
                isPhotoChoosing = true
            )
        }
    }

    // Выбор фото из галлереи
    fun onPhotoChoose() {
        _state.update { currentState ->
            currentState.copy(
                isPhotoChoosing = false
            )
        }

        viewModelScope.launch {
            _events.emit(MenuEvent.ChoosePhotoFromGallery)
        }
    }

    // Закрытие диалогового окна выбора фото
    fun onPhotoChooseDismiss() {
        _state.update { currentState ->
            currentState.copy(
                isPhotoChoosing = false
            )
        }
    }

    // Изменение фамилии
    fun onSurnameChange(newSurname: String) {
        surname = newSurname
        updateSaveButton()
    }

    // Изменении имени
    fun onNameChange(newName: String) {
        name = newName
        updateSaveButton()
    }

    // Изменение отчества
    fun onLastNameChange(newLastName: String) {
        lastName = newLastName
        updateSaveButton()
    }

    // Выбор пола Мужчина
    fun onMaleSelected() {
        _state.update { currentState ->
            currentState.copy(
                gender = Gender.Male
            )
        }

        updateSaveButton()
    }

    // Выбор пола Женщина
    fun onFemaleSelected() {
        _state.update { currentState ->
            currentState.copy(
                gender = Gender.Female
            )
        }

        updateSaveButton()
    }

    // Выбор даты рождения
    fun onBirthDateSelected(date: LocalDate) {
        _state.update { currentState ->
            currentState.copy(
                selectedBirthDate = if (date > LocalDate.now())
                    LocalDate.now()
                else
                    date
            )
        }

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        birthDate = _state.value.selectedBirthDate.format(formatter)

        updateSaveButton()
    }

    // Изменение роста
    fun onHeightChange(newHeight: String) {
        val formattedNewHeight = newHeight
            .replace("-", "")
            .replace(",", ".")
            .trim()

        height =
            if (formattedNewHeight.toDoubleOrNull() != null && formattedNewHeight.toDouble() < 300.0
                || formattedNewHeight.isEmpty()
            ) {
                formattedNewHeight
            } else
                height

        updateSaveButton()
    }

    // Изменение веса
    fun onWeightChange(newWeight: String) {
        val formattedNewWeight = newWeight
            .replace("-", "")
            .replace(",", ".")
            .trim()

        weight =
            if (formattedNewWeight.toDoubleOrNull() != null && formattedNewWeight.toDouble() < 300.0
                || formattedNewWeight.isEmpty()
            ) {
                formattedNewWeight
            } else
                weight

        updateSaveButton()
    }

    // Обновление активности кнопки сохранения
    private fun updateSaveButton() {
        _state.update { currentState ->
            currentState.copy(
                isSaveActive = surname.isNotEmpty() &&
                        name.isNotEmpty() &&
                        lastName.isNotEmpty()
                //true //surname.isNotEmpty() //||
                /*name.isNotEmpty() ||
                lastName.isNotEmpty() ||
                currentState.gender != null ||
                birthDate.isNotEmpty() ||
                height.isNotEmpty() ||
                weight.isNotEmpty()*/
            )
        }
    }

    // Сохранение профиля
    fun onSave() {
        viewModelScope.launch(Dispatchers.IO) {
            _menuUseCase.updateProfile(
                surname = surname,
                name = name,
                lastName = lastName,
                email = email,
                gender = if (_state.value.gender == Gender.Female) 2 else 1,
                birthDate = birthDate,
                height = height.toFloat(),
                weight = weight.toFloat()
            )
        }
    }

    // Выход из аккаунта
    fun onLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            _menuUseCase.logout()
            _events.emit(MenuEvent.Logout)
        }
    }
}