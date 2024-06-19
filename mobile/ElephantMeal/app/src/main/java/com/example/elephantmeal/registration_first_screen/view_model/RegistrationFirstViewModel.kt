package com.example.elephantmeal.registration_first_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.elephantmeal.registration_first_screen.domain.RegistrationFirstUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationFirstViewModel @Inject constructor(
    private val _registrationFirstUseCase: RegistrationFirstUseCase
): ViewModel() {
    private val _state = MutableStateFlow(RegistrationFirstUiState())
    val state = _state.asStateFlow()

    var surname by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set


    // Редактирование фамилии
    fun editSurname(newSurname: String) {
        surname = newSurname
        changeContinueButtonEnabled()
    }

    // Редактирование имени
    fun editName(newName: String) {
        name = newName
        changeContinueButtonEnabled()
    }

    // Редактирование отчества
    fun editLastName(newLastName: String) {
        lastName = newLastName
        changeContinueButtonEnabled()
    }

    // Редактирование email
    fun editEmail(newEmail: String) {
        email = newEmail
        changeContinueButtonEnabled()

        _state.update { currentState ->
            currentState.copy(
                isEmailValid = true
            )
        }
    }

    // Изменение активности кнопки продолжения
    private fun changeContinueButtonEnabled() {
        _state.update { currentState ->
            currentState.copy(
                isContinueEnabled = surname.isNotEmpty() &&
                name.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()
            )
        }
    }

    // Продолжение регистрации
    fun onContinueButtonClick() {
        val isEmailValid = _registrationFirstUseCase.isValidEmail(email)

        _state.update { currentState ->
            currentState.copy(
                isEmailValid = isEmailValid,
                isLoggedIn = isEmailValid
            )
        }
    }
}