package com.example.elephantmeal.registration_third_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elephantmeal.registration_third_screen.domain.use_case.RegistrationThirdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationThirdViewModel @Inject constructor(
    private val _registrationThirdUseCase: RegistrationThirdUseCase
): ViewModel() {
    private val _state = MutableStateFlow(RegistrationThirdUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<RegistrationThirdEvent>()
    val events = _events.asSharedFlow()

    var password by mutableStateOf("")
        private set

    var passwordConfirmation by mutableStateOf("")
        private set

    // Изменение пароля
    fun onPasswordChange(newPassword: String) {
        password = newPassword
        changeRegistrationButtonEnabled()

        _state.update { currentState ->
            currentState.copy(
                arePasswordsValid = true
            )
        }
    }

    // Изменение подтверждения пароля
    fun onPasswordConfirmationChange(newPasswordConfirmation: String) {
        passwordConfirmation = newPasswordConfirmation
        changeRegistrationButtonEnabled()

        _state.update { currentState ->
            currentState.copy(
                arePasswordsValid = true
            )
        }
    }

    // Изменение видимости пароля
    fun onPasswordVisibilityChange() {
        _state.update { currentState ->
            currentState.copy(
                isPasswordVisible = !currentState.isPasswordVisible
            )
        }
    }

    // Изменение активности кнопки регистрации
    private fun changeRegistrationButtonEnabled() {
        _state.update { currentState ->
            currentState.copy(
                isRegisterButtonEnabled = password.isNotEmpty() && passwordConfirmation.isNotEmpty()
            )
        }
    }

    // Регистрация
    fun onRegister(
        surname: String,
        name: String,
        lastName: String,
        email: String,
        gender: Int?,
        weight: Float?,
        height: Float?,
        birthDate: String?
    ) {
        if (_registrationThirdUseCase.doPasswordsMatch(password, passwordConfirmation)) {
            viewModelScope.launch(Dispatchers.IO) {
                val isRegistered = _registrationThirdUseCase.register(
                    surname,
                    name,
                    lastName,
                    email,
                    gender,
                    weight,
                    height,
                    birthDate,
                    password
                )

                if (isRegistered)
                    _events.emit(RegistrationThirdEvent.IsRegistered)
            }
        }
        else {
            _state.update { currentState ->
                currentState.copy(
                    arePasswordsValid = false
                )
            }
        }
    }
}