package com.example.elephantmeal.products_ban_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elephantmeal.products_ban_screen.domain.Category
import com.example.elephantmeal.products_ban_screen.domain.Product
import com.example.elephantmeal.products_ban_screen.domain.ProductsBanUseCase
import com.example.elephantmeal.products_ban_screen.domain.Subcategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private var _selectedCategoryIndex = 0

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

        val hints = _productsBanUseCase.searchProducts(searchValue)

        viewModelScope.launch(Dispatchers.Default) {
            _state.update { currentState ->
                currentState.copy(
                    hints = hints,
                    isSearchVisible = hints.isNotEmpty() && searchValue.isNotEmpty()
                )
            }
        }
    }

    // Очистка поисковой строки
    fun onSearchCleared() {
        searchField = ""

        _state.update { currentState ->
            currentState.copy(
                isSearchVisible = false,
            )
        }
    }

    // Выбор категории
    fun onCategorySelected(selectedIndex: Int) {
        _selectedCategoryIndex = selectedIndex
        val categories = _productsBanUseCase.selectCategory(selectedIndex)

        _state.update { currentState ->
            currentState.copy(
                categories = categories,
                subcategories = categories[selectedIndex].subcategories,
                hints = _productsBanUseCase.searchProducts(searchField)
            )
        }
    }

    // Выбор всех подкатегорий
    fun selectAllSubcategories() {
        val newCategories = _productsBanUseCase.selectAllSubcategories(_selectedCategoryIndex)

        _state.update { currentState ->
            currentState.copy(
                categories = newCategories,
                subcategories = newCategories[_selectedCategoryIndex].subcategories,
                hints = _productsBanUseCase.searchProducts(searchField)
            )
        }
    }

    // Выбор продукта
    fun selectProduct(subcategoryIndex: Int, productIndex: Int) {
        val newSubcategories = _productsBanUseCase.selectProduct(
            _selectedCategoryIndex,
            subcategoryIndex,
            productIndex
        )

        updateSubcategoriesSelection(newSubcategories)
    }

    // Выбор всех продуктов подкатегории
    fun selectAllProducts(subcategoryIndex: Int) {
        val newSubcategories =
            _productsBanUseCase.selectAllProducts(_selectedCategoryIndex, subcategoryIndex)

        updateSubcategoriesSelection(newSubcategories)
    }

    // Обновление выделения продуктов и подкатегорий
    private fun updateSubcategoriesSelection(newSubcategories: List<Subcategory>) {
        _state.update { currentState ->
            currentState.copy(
                subcategories = newSubcategories,
                categories = currentState.categories.mapIndexed { index, category ->
                    if (index == _selectedCategoryIndex)
                        category.copy(subcategories = newSubcategories)
                    else
                        category
                },
                hints = _productsBanUseCase.searchProducts(searchField)
            )
        }
    }

    // Выбор всех продуктов из подсказки
    fun selectAllHints() {
        val isSelected = _state.value.hints.count { it.isSelected } != _state.value.hints.size

        val newCategories = _productsBanUseCase.selectHints(
            hints = _state.value.hints.map { it.name },
            isSelected = isSelected
        )

        _state.update { currentState ->
            currentState.copy(
                categories = newCategories,
                subcategories = newCategories[_selectedCategoryIndex].subcategories,
                hints = currentState.hints.map {
                    it.copy(isSelected = isSelected)
                }
            )
        }
    }

    // Выбор продукта по подсказдке
    fun selectHint(hint: Product) {
        val newCategories = _productsBanUseCase.selectHints(listOf(hint.name), !hint.isSelected)

        _state.update { currentState ->
            currentState.copy(
                categories = newCategories,
                subcategories = newCategories[_selectedCategoryIndex].subcategories,
                hints = currentState.hints.map {
                    if (it.name == hint.name)
                        it.copy(
                            isSelected = !it.isSelected
                        )
                    else
                        it
                }
            )
        }
    }
}