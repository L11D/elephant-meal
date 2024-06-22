package com.example.elephantmeal.products_ban_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.elephantmeal.products_ban_screen.domain.Category
import com.example.elephantmeal.products_ban_screen.domain.ProductsBanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductsBanViewModel @Inject constructor(
    private val _productsBanUseCase: ProductsBanUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProductsBanUiState())
    val state = _state.asStateFlow()

    var searchField by mutableStateOf("")
        private set

    private val _categories = mutableListOf<Category>()

    init {
        _categories.addAll(_productsBanUseCase.getCategories())
        _categories[0] = _categories[0].copy(isSelected = true)

        _state.update { currentState ->
            currentState.copy(
                categories = _categories,
                subcategories = _categories[0].subcategories
            )
        }
    }

    // Изменение поисковой строки
    fun onSearchFieldChange(searchValue: String) {
        searchField = searchValue
    }

    // Выбор категории
    fun onCategorySelected(selectedIndex: Int) {
        _state.update { currentState ->
            currentState.copy(
                categories = currentState.categories.mapIndexed { index, category ->
                    if (index == selectedIndex)
                        category.copy(isSelected = true)
                    else
                        category.copy(isSelected = false)
                },
                subcategories = currentState.categories[selectedIndex].subcategories
            )
        }
    }

    // Выбор всех подкатегорий
    fun selectAllSubcategories() {

    }

    // Выбор подкатегории
    fun selectSubcategory(index: Int) {

    }
}