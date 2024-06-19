package com.example.elephantmeal.registration_second_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
class RegistrationSecondViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(RegistrationSecondUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<RegistrationSecondEvent>()
    val events = _events.asSharedFlow()

    var birthDate by mutableStateOf("")
        private set

    var height by mutableStateOf("")
        private set

    var weight by mutableStateOf("")
        private set

    // Выбор пола Мужчина
    fun onMaleSelected() {
        _state.update { currentState ->
            currentState.copy(
                gender = Gender.Male
            )
        }

        updateFilling()
    }

    // Выбор пола Женщина
    fun onFemaleSelected() {
        _state.update { currentState ->
            currentState.copy(
                gender = Gender.Female
            )
        }

        updateFilling()
    }

    // Выбор даты рождения
    fun selectBirthDate(date: LocalDate) {
        _state.update { currentState ->
            currentState.copy(
                selectedDate = if (date > LocalDate.now())
                    LocalDate.now()
                else
                    date
            )
        }

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        birthDate = _state.value.selectedDate.format(formatter)

        updateFilling()
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

        updateFilling()
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

        updateFilling()
    }

    // Проверка заполненности полей
    private fun updateFilling() {
        _state.update { currentState ->
            currentState.copy(
                isFilled = _state.value.gender != null ||
                    birthDate.isNotEmpty() ||
                    height.isNotEmpty() ||
                    weight.isNotEmpty()
            )
        }
    }

    // Переход на следующий экран
    fun onContinueButtonClick() {
        viewModelScope.launch {
            _events.emit(RegistrationSecondEvent.Continue)
        }
    }
}