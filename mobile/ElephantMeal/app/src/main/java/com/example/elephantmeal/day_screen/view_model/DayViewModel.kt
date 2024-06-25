package com.example.elephantmeal.day_screen.view_model

import androidx.lifecycle.ViewModel
import com.example.elephantmeal.day_screen.domain.DayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val _todayUseCase: DayUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DayUiState())
    val state = _state.asStateFlow()

    init {
        selectWeek()

        _state.update { currentState ->
            currentState.copy(
                selectedDate = LocalDate.now(),
                weekRation = _todayUseCase.getWeekRation(_state.value.selectedWeek),
                dayRation = _todayUseCase.getDayRation(LocalDate.now())
            )
        }
    }

    // Выбор даты
    fun selectDate(date: LocalDate) {
        _state.update { currentState ->
            currentState.copy(
                selectedDate = date,
                dayRation = _todayUseCase.getDayRation(date)
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