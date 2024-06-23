package com.example.elephantmeal.products_preferences_screen.domain

import com.example.elephantmeal.products_ban_screen.domain.Category
import com.example.elephantmeal.products_ban_screen.domain.Product
import com.example.elephantmeal.products_ban_screen.domain.Subcategory
import java.util.Locale

class ProductsPreferencesUseCase {
    private var _categories = mutableListOf(
        generateCategory(),
        generateCategory(),
        generateCategory(),
        generateCategory(),
        generateCategory()
    )

    fun getCategories(): List<Category> {
        return _categories
    }

    private var _allProducts = listOf<Product>()

    init {
        _allProducts = _categories.flatMap {
            it.subcategories.flatMap {
                it.products
            }
        }.distinct()
    }

    private fun updateAllProducts() {
        _allProducts = _categories.flatMap {
            it.subcategories.flatMap {
                it.products
            }
        }.sortedBy { !it.isSelected }.distinctBy { it.name }
    }

    // Выбор категории
    fun selectCategory(selectedIndex: Int): List<Category> {
        _categories = _categories.mapIndexed { index, category ->
            if (index == selectedIndex)
                category.copy(isSelected = true)
            else
                category.copy(isSelected = false)
        }.toMutableList()

        return _categories
    }

    // Выбор всех подкатегорий
    fun selectAllSubcategories(categoryIndex: Int): List<Category> {
        val category = _categories[categoryIndex]

        val areSelected =
            category.subcategories.count { it.isSelected } < category.subcategories.size

        _categories[categoryIndex] = category.copy(
            isSelected = true,
            subcategories = category.subcategories.map {
                it.copy(
                    isSelected = areSelected,
                    products = it.products.map {
                        it.copy(
                            isSelected = areSelected
                        )
                    }
                )
            }
        )

        updateAllProducts()

        return _categories
    }

    // Выбор продукта
    fun selectProduct(
        categoryIndex: Int,
        subcategoryIndex: Int,
        productIndex: Int
    ): List<Subcategory> {
        var category = _categories[categoryIndex]
        var isSelected: Boolean

        _categories[categoryIndex] = category.copy(
            subcategories = category.subcategories.mapIndexed { subcategoryMapIndex, subcategory ->
                if (subcategoryMapIndex == subcategoryIndex)
                    subcategory.copy(
                        products = subcategory.products.mapIndexed { productMapIndex, product ->
                            if (productMapIndex == productIndex) {
                                isSelected = !product.isSelected
                                product.copy(isSelected = isSelected)
                            } else
                                product
                        }
                    )
                else
                    subcategory
            }
        )

        category = _categories[categoryIndex]

        _categories[categoryIndex] = category.copy(
            subcategories = category.subcategories.mapIndexed { subcategoryMapIndex, subcategory ->
                if (subcategoryMapIndex == subcategoryIndex)
                    subcategory.copy(
                        isSelected =
                        subcategory.products.count { it.isSelected } == subcategory.products.size
                    )
                else
                    subcategory
            }
        )

        updateAllProducts()

        return _categories[categoryIndex].subcategories
    }

    // Выбор всех продуктов подкатегории
    fun selectAllProducts(categoryIndex: Int, subcategoryIndex: Int): List<Subcategory> {
        val category = _categories[categoryIndex]

        _categories[categoryIndex] = category.copy(
            subcategories = category.subcategories.mapIndexed { index, subcategory ->
                if (index == subcategoryIndex)
                    subcategory.copy(
                        products = subcategory.products.map { it.copy(isSelected = !subcategory.isSelected) },
                        isSelected = !subcategory.isSelected
                    )
                else
                    subcategory
            }
        )

        updateAllProducts()

        return _categories[categoryIndex].subcategories
    }

    // Поиск продуктов
    fun searchProducts(query: String): List<Product> {
        return _allProducts.filter {
            it.name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
        }.distinct()
    }

    // Выбор подсказки
    fun selectHints(hints: List<String>, isSelected: Boolean): List<Category> {
        _categories = _categories.map {
            it.copy(
                subcategories = it.subcategories.map {
                    it.copy(
                        products = it.products.map {
                            if (it.name in hints) {
                                it.copy(
                                    isSelected = isSelected
                                )
                            } else {
                                it
                            }
                        }
                    )
                }
            )
        }.toMutableList()

        _categories = _categories.map {
            it.copy(
                subcategories = it.subcategories.map {
                    it.copy(
                        isSelected = it.products.count { it.isSelected } == it.products.size
                    )
                }
            )
        }.toMutableList()

        updateAllProducts()

        return _categories
    }

    private fun generateCategory(): Category = Category(
        name = "Шоколад",
        isSelected = false,
        subcategories = listOf(
            Subcategory(
                name = "Белый шоколад",
                isSelected = false,
                products = listOf(
                    Product(
                        name = "Твикс",
                        isSelected = false
                    ),
                    Product(
                        name = "Сникерс",
                        isSelected = false
                    ),
                    Product(
                        name = "Смешные цены",
                        isSelected = false
                    ),
                )
            ),
            Subcategory(
                name = "Черный шоколад",
                isSelected = false,
                products = listOf(
                    Product(
                        name = "Твикс",
                        isSelected = false
                    ),
                    Product(
                        name = "Сникерс",
                        isSelected = false
                    ),
                    Product(
                        name = "Смешные цены",
                        isSelected = false
                    ),
                )
            ),
            Subcategory(
                name = "Молочный шоколад",
                isSelected = false,
                products = listOf(
                    Product(
                        name = "Твикс",
                        isSelected = false
                    ),
                    Product(
                        name = "Сникерс",
                        isSelected = false
                    ),
                    Product(
                        name = "Смешные цены",
                        isSelected = false
                    ),
                )
            )
        )
    )
}