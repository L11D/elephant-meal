package com.example.elephantmeal.today_screen.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elephantmeal.today_screen.domain.TodayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val _todayUseCase: TodayUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TodayUiState())
    val state = _state.asStateFlow()

    init {
        _state.update { currentState ->
            currentState.copy(
                selectedDate = LocalDate.now(),
                weekRation = _todayUseCase.getWeekRation(),
                dayRation = _todayUseCase.getDayRation()
            )
        }

        selectWeek()
    }

    // Выбор даты
    fun selectDate(date: LocalDate) {
        _state.update { currentState ->
            currentState.copy(
                selectedDate = date
            )
        }

        selectWeek()
    }

    // Получение недели, содержащей выбранную дату
    private fun selectWeek() {
        val currentDate = _state.value.selectedDate
        val startOfWeek = currentDate.minusDays(currentDate.dayOfWeek.value.toLong() - 1)
        val week: MutableList<LocalDate> = mutableListOf()

        for (i in 0..6) week.add(startOfWeek.plusDays(i.toLong()))

        _state.update { currentState ->
            currentState.copy(
                selectedWeek = week
            )
        }
    }

    // Перевод номера дня недели в его название
    fun getDayOfWeekName(dayOfWeek: Int): Int {
        return _todayUseCase.getDayOfWeekName(dayOfWeek)
    }
}