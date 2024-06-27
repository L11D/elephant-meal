package com.example.elephantmeal.confirm_screen.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
) : ViewModel() {
    private val _events = MutableSharedFlow<ConfirmEvent>()
    val events = _events.asSharedFlow()

    // Подтверждение регистрации
    fun confirm() {
        viewModelScope.launch(Dispatchers.IO) {

            _events.emit(ConfirmEvent.Confirmed)
        }
    }
}