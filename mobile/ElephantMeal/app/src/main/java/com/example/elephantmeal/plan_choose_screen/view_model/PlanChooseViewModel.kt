package com.example.elephantmeal.plan_choose_screen.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elephantmeal.plan_choose_screen.domain.MealPlan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanChooseViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(PlanChooseUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<PlanChooseEvent>()
    val events = _events.asSharedFlow()

    init {
        _state.update { currentState ->
            currentState.copy(
                mealPlans = listOf(
                    MealPlan(
                        name = "Масса",
                        description = "Питание с профицитом калорий. Это питание используется " +
                                "для набора мышечной массы."
                    ),
                    MealPlan(
                        name = "Сушка",
                        description = "Питание с дефицитом калорий. Это питание используется " +
                                "для снижения процента жира в организме, чтобы похудеть или " +
                                "иметь более рельефную фигуру."
                    ),
                    MealPlan(
                        name = "Сбалансированное питание",
                        description = "Питание без профицита или дефицита калорий, нужно тем, " +
                                "кто хочет сохранять свою текущую форму."
                    )
                )
            )
        }
    }

    // Выбор плана питания
    fun onMeanPlanSelected(selectedIndex: Int) {
        _state.update { currentState ->
            currentState.copy(
                selectedIndex = selectedIndex,
                isContinueButtonEnabled = true
            )
        }
    }

    // Продолжение составления плана питания
    fun onContinuteButtonClick() {
        viewModelScope.launch {
            _events.emit(PlanChooseEvent.Continue)
        }
    }
}