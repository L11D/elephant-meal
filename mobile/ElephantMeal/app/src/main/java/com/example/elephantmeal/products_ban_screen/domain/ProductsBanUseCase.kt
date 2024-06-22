package com.example.elephantmeal.products_ban_screen.domain

class ProductsBanUseCase {
    private var _categories = mutableListOf(
        getCategory(),
        getCategory(),
        getCategory(),
        getCategory(),
        getCategory()
    )

    fun getCategories(): List<Category> {
        return _categories
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

    // Выбор продукта
    fun selectProduct(
        categoryIndex: Int,
        subcategoryIndex: Int,
        productIndex: Int
    ): List<Subcategory> {
        var category = _categories[categoryIndex]

        _categories[categoryIndex] = category.copy(
            subcategories = category.subcategories.mapIndexed { subcategoryMapIndex, subcategory ->
                if (subcategoryMapIndex == subcategoryIndex)
                    subcategory.copy(
                        products = subcategory.products.mapIndexed { productMapIndex, product ->
                            if (productMapIndex == productIndex)
                                product.copy(isSelected = !product.isSelected)
                            else
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

        return _categories[categoryIndex].subcategories
    }

    private fun getCategory(): Category = Category(
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