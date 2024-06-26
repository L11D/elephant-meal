package com.example.elephantmeal.week_screen.view_model

import androidx.lifecycle.ViewModel
import com.example.elephantmeal.day_screen.domain.Mealtime
import com.example.elephantmeal.week_screen.domain.WeekUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WeekViewModel @Inject constructor(
    private val _weekUseCase: WeekUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WeekUiState())
    val state = _state.asStateFlow()

    private var currentDate = LocalDate.now()

    // Получение расписания рациона на неделю
    fun getWeekTimetable(date: LocalDate) {
        currentDate = date
        updateWeekRation()
    }

    // Получение расписания рациона на предыдущей неделе
    fun getPreviousWeekRation() {
        currentDate = currentDate.minusDays(7)
        updateWeekRation()
    }

    // Получение расписания рациона на следующей неделе
    fun getNextWeekRation() {
        currentDate = currentDate.plusDays(7)
        updateWeekRation()
    }

    // Получение рациона на неделю
    private fun updateWeekRation() {
        val week = _weekUseCase.getWeek(currentDate)
        val weekRation = _weekUseCase.getWeekRation(week)

        _state.update { currentState ->
            currentState.copy(
                weekRation = weekRation,
                week = week.map { _weekUseCase.formatDate(it) },
                maxMealtimes = _weekUseCase.getMealtimeAmount(weekRation)
            )
        }
    }

    // Открытие диалога просмотра приёма пищи
    fun showMealtimeDialog(mealtime: Mealtime) {
        _state.update { currentState ->
            currentState.copy(
                isMealtimeDialogVisible = true,
                dialogMealtime = mealtime
            )
        }
    }

    // Закрытие диалога просмотра приёма пищи
    fun dismissMealtimeDialog() {
        _state.update { currentState ->
            currentState.copy(
                isMealtimeDialogVisible = false
            )
        }
    }
}