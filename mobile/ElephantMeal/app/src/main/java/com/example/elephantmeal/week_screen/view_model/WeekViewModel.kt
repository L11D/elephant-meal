package com.example.elephantmeal.week_screen.view_model

import androidx.lifecycle.ViewModel
import com.example.elephantmeal.week_screen.domain.WeekUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WeekViewModel @Inject constructor(
    private val _weekUseCase: WeekUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WeekUiState())
    val state = _state.asStateFlow()

    init {
        _state.update { currentState ->
            val week = _weekUseCase.getCurrentWeek()

            currentState.copy(
                weekRation = _weekUseCase.getWeekRation(week),
                weekStart = _weekUseCase.formatDate(week.first()),
                weekEnd = _weekUseCase.formatDate(week.last())
            )
        }
    }
}