package com.example.elephantmeal.login_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elephantmeal.login_screen.domain.models.LoginCredentials
import com.example.elephantmeal.login_screen.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _loginUseCase: LoginUseCase
): ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    // Ввод email
    fun onEmailChange(newEmail: String) {
        email = newEmail
        changeLoginButtonEnable()

        _state.update { currentState ->
            currentState.copy(
                isEmailValid = true
            )
        }
    }

    // Ввод пароля
    fun onPasswordChange(newPassword: String) {
        password = newPassword
        changeLoginButtonEnable()
    }

    // Скрытие/показ пароля
    fun onShowPasswordButtonClick() {
        _state.update { currentState ->
            currentState.copy(
                isPasswordVisible = !currentState.isPasswordVisible
            )
        }
    }

    // Восстановление пароля
    fun onResetPasswordClick() {

    }

    // Изменение активности кнопки авторизации
    private fun changeLoginButtonEnable() {
        _state.update { currentState ->
            currentState.copy(
                isLoginEnabled = password.isNotEmpty() && email.isNotEmpty()
            )
        }
    }

    // Авторизация
    fun onLoginButtonClick() {
        val isEmailValid = _loginUseCase.isEmailValid(email)

        viewModelScope.launch(Dispatchers.IO) {
            val isLoggedIn = _loginUseCase.login(
                LoginCredentials(
                    email = email,
                    password = password
                )
            )

            if (isLoggedIn) {
                _state.update { currentState ->
                    currentState.copy(
                        isEmailValid = isEmailValid,
                        isLoggedIn = isEmailValid
                    )
                }
            }
        }
    }
}