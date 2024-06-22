package com.example.elephantmeal.products_ban_screen.domain

class ProductsBanUseCase {
    fun getCategories(): List<Category> {
        return listOf(
            getCategory(),
            getCategory(),
            getCategory(),
            getCategory(),
            getCategory()
        )
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