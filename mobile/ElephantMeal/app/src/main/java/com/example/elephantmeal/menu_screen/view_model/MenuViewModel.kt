package com.example.elephantmeal.menu_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.elephantmeal.registration_second_screen.view_model.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(MenuUiState())
    val state = _state.asStateFlow()

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

    // Выбор фото
    fun choosePhoto() {

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

        height = if (formattedNewHeight.toDoubleOrNull() != null && formattedNewHeight.toDouble() < 300.0
            || formattedNewHeight.isEmpty()) {
            formattedNewHeight
        }
        else
            height

        updateSaveButton()
    }

    // Изменение веса
    fun onWeightChange(newWeight: String) {
        val formattedNewWeight = newWeight
            .replace("-", "")
            .replace(",", ".")
            .trim()

        weight = if (formattedNewWeight.toDoubleOrNull() != null && formattedNewWeight.toDouble() < 300.0
            || formattedNewWeight.isEmpty()) {
            formattedNewWeight
        }
        else
            weight

        updateSaveButton()
    }

    // Обновление активности кнопки сохранения
    private fun updateSaveButton() {
        _state.update { currentState ->
            currentState.copy(
                isSaveActive = surname.isNotEmpty() ||
                name.isNotEmpty() ||
                lastName.isNotEmpty() ||
                currentState.gender != null ||
                birthDate.isNotEmpty() ||
                height.isNotEmpty() ||
                weight.isNotEmpty()
            )
        }
    }

    // Сохранение профиля
    fun onSave() {

    }
}