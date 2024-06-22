package com.example.elephantmeal.cheat_meal_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elephantmeal.cheat_meal_screen.domain.DayOfWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheatMealViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(CheatMealUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<CheatMealEvent>()
    val events = _events.asSharedFlow()

    private val _selectedDaysOfWeek = mutableListOf<DayOfWeek>()

    var cheatMealAmount by mutableStateOf(0.0f)
        private set

    // Выбор дня недели
    fun onDayOfWeekSelected(dayOfWeek: DayOfWeek) {
        if (dayOfWeek in _selectedDaysOfWeek) {
            _selectedDaysOfWeek.remove(dayOfWeek)
        } else {
            _selectedDaysOfWeek.add(dayOfWeek)
        }

        _state.update { currentState ->
            currentState.copy(
                selectedDaysOfWeek = _selectedDaysOfWeek.toList()
            )
        }
    }

    // Изменение кол-ва чит мила
    fun onCheatMealChange(newAmount: Float) {
        cheatMealAmount = newAmount

        _state.update { currentState ->
            currentState.copy(
                cheatMealAmount = ((currentState.maxCheatMeal - currentState.minCheatMeal) * newAmount).toInt()
            )
        }
    }

    // Сохранение настроек чит мила
    fun onContinueButtonClick() {
        viewModelScope.launch {
            _events.emit(CheatMealEvent.Continue)
        }
    }
}