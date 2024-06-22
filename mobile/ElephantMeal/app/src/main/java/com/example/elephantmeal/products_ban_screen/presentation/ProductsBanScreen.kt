package com.example.elephantmeal.products_ban_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.common.presentation.SearchField
import com.example.elephantmeal.products_ban_screen.domain.Category
import com.example.elephantmeal.products_ban_screen.domain.Product
import com.example.elephantmeal.products_ban_screen.domain.Subcategory
import com.example.elephantmeal.products_ban_screen.view_model.ProductsBanViewModel
import com.example.elephantmeal.registration_second_screen.presentation.LogoWithBackButton
import com.example.elephantmeal.ui.theme.DarkBlueColor
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.DeselectedCategoryColor
import com.example.elephantmeal.ui.theme.LightBlueColor
import com.example.elephantmeal.ui.theme.LightGrayColor

// Экран выбора непредпочитаемых продуктов
@Composable
fun ProductsBanScreen(
    onBackButtonClick: () -> Unit,
    viewModel: ProductsBanViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Логотип приложения
        LogoWithBackButton(
            onBackButtonClick = onBackButtonClick
        )

        // Заголовок экрана
        Text(
            modifier = Modifier
                .padding(24.dp, 16.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.products_ban_header),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Строка поиска
        SearchField(
            label = stringResource(id = R.string.search_by_name),
            topPadding = 24.dp,
            value = viewModel.searchField,
            onValueChange = {
                viewModel.onSearchFieldChange(it)
            }
        )

        Box {
            // Категории
            Categories()
        }

        Box(
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 8.dp)
                .weight(1f)
        ) {
            // Подкатегории
            Subcategories()

            // Сглаживание краёв списка белой тенью
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.White, Color.Transparent),
                            start = Offset(0.0f, 0.0f),
                            end = Offset(0.0f, Float.POSITIVE_INFINITY)
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.White, Color.Transparent),
                            start = Offset(0.0f, Float.POSITIVE_INFINITY),
                            end = Offset(0.0f, 0.0f)
                        )
                    )
            )
        }

        // Кнопка продолжить
        PrimaryButton(
            bottomPadding = 32.dp,
            text = stringResource(id = R.string.continuation),
            onClick = {
                // TODO
            }
        )
    }
}

// Категории
@Composable
fun Categories(
    viewModel: ProductsBanViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Row(
        modifier = Modifier
            .padding(0.dp, 32.dp, 0.dp, 0.dp)
            .fillMaxWidth()
            .horizontalScroll(
                rememberScrollState()
            ),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        state.categories.forEachIndexed { index, category ->
            CategoryElement(
                paddingStart = if (index == 0) 24.dp else 0.dp,
                paddingEnd = if (index == state.categories.size - 1) 24.dp else 0.dp,
                name = category.name,
                number = index + 1,
                isSelected = category.isSelected,
                onClick = {
                    viewModel.onCategorySelected(index)
                }
            )
        }
    }
}

// Элемент категории
@Composable
fun CategoryElement(
    paddingStart: Dp = 0.dp,
    paddingEnd: Dp = 0.dp,
    name: String,
    number: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .padding(paddingStart, 0.dp, paddingEnd, 0.dp)
    ) {
        // Номер категории
        Box(
            modifier = Modifier
                .size(64.dp, 64.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(
                    if (isSelected)
                        LightBlueColor
                    else
                        DeselectedCategoryColor
                )
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    onClick()
                }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = number.toString(),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        // Название категории
        Text(
            modifier = Modifier
                .padding(0.dp, 16.dp, 0.dp, 0.dp),
            text = name,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = if (isSelected)
                    Color.Black
                else
                    DarkBlueColor
            )
        )
    }
}

// Подкатегории
@Composable
fun Subcategories(
    viewModel: ProductsBanViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .padding(24.dp, 0.dp, 24.dp, 0.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        val interactionSource = remember {
            MutableInteractionSource()
        }

        // Кнопка выбора всех подкатегорий
        Box(
            modifier = Modifier
                .padding(0.dp, 24.dp, 0.dp, 0.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = LightGrayColor
                )
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    viewModel.selectAllSubcategories()
                }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 16.dp, 16.dp),
                text = stringResource(id = R.string.select_all_subcategories),
                style = TextStyle(
                    fontSize = 24.sp
                )
            )
        }

        // Перечисление категорий
        state.subcategories.forEachIndexed { index, subcategory ->
            SubcategoryElement(
                subcategory = subcategory,
                onClick = {
                    viewModel.selectSubcategory(index)
                },
                onProductSelected = {

                }
            )
        }
        
        Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp))
    }
}

// Подкатегория
@Composable
fun SubcategoryElement(
    subcategory: Subcategory,
    onClick: () -> Unit,
    onProductSelected: (productIndex: Int) -> Unit
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .padding(0.dp, 24.dp, 0.dp, 0.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = LightGrayColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 16.dp)
        ) {
            // Название подкатегории
            Text(
                modifier = Modifier
                    .padding(16.dp, 16.dp, 16.dp, 0.dp),
                text = subcategory.name,
                style = TextStyle(
                    fontSize = 24.sp
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // Кнопка раскрытия списка продуктов
            Image(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 16.dp, 0.dp)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape)
                    .clickable {
                        isExpanded = !isExpanded
                    },
                imageVector = ImageVector.vectorResource(
                    id = if (isExpanded) R.drawable.collapse_icon
                    else
                        R.drawable.expand_icon
                ),
                contentDescription = stringResource(id = R.string.expand_button)
            )
        }

        // Продукты подкатегории
        SelectAllProducts(subcategory = subcategory)

        subcategory.products.forEachIndexed { index, product ->
            SubcategoryProductElement(
                product = product,
                onProductSelected = {
                    onProductSelected(index)
                }
            )
        }
    }
}

// Продукт подкатегории
@Composable
fun SubcategoryProductElement(
    product: Product,
    onProductSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    ) {
        // Чекбокс выбора продукта
        Checkbox(
            modifier = Modifier
                .size(24.dp, 24.dp)
                .align(Alignment.CenterVertically),
            colors = CheckboxDefaults.colors(
                uncheckedColor = DarkGrayColor
            ),
            checked = product.isSelected,
            onCheckedChange = {
                onProductSelected()
            }
        )

        // Название продукта
        Text(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .align(Alignment.CenterVertically),
            text = product.name,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

// Выбор всех продуктов
@Composable
fun SelectAllProducts(
    subcategory: Subcategory,
) {
    Row(
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    ) {
        // Чекбокс выбора продукта
        Checkbox(
            modifier = Modifier
                .size(24.dp, 24.dp)
                .align(Alignment.CenterVertically),
            colors = CheckboxDefaults.colors(
                uncheckedColor = DarkGrayColor
            ),
            checked = subcategory.products.count { it.isSelected } == subcategory.products.size,
            onCheckedChange = {
                // TODO
            }
        )

        // Надпись Выбрать все продукты
        Text(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(id = R.string.select_all_products),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}