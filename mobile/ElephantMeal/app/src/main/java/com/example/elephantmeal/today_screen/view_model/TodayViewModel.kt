package com.example.elephantmeal.today_screen.view_model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TodayUiState())
    val state = _state.asStateFlow()

    private var weekModeIsSet = false

    // Выбор изначального режима экрана
    fun setWeekMode(isWeekMode: Boolean) {
        if (!weekModeIsSet) {
            _state.update { currentState ->
                currentState.copy(
                    isWeekModeSelected = isWeekMode
                )
            }

            weekModeIsSet = true
        }
    }

    // Переход в режим расписания на неделю
    fun onWeekModeSelected(selectedDate: LocalDate) {
        _state.update { currentState ->
            currentState.copy(
                isWeekModeSelected = true,
                selectedDate = selectedDate
            )
        }
    }

    // Переход в режим расписания на день
    fun onDayModeSelected() {
        _state.update { currentState ->
            currentState.copy(
                isWeekModeSelected = false
            )
        }
    }
}