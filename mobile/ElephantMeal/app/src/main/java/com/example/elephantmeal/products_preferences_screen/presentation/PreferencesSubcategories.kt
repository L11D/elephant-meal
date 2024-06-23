package com.example.elephantmeal.products_preferences_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.products_ban_screen.domain.Product
import com.example.elephantmeal.products_ban_screen.domain.Subcategory
import com.example.elephantmeal.products_ban_screen.view_model.ProductsBanViewModel
import com.example.elephantmeal.products_preferences_screen.view_model.ProductsPreferencesViewModel
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.LightGrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor


// Подкатегории
@Composable
fun PreferencesSubcategories(
    viewModel: ProductsPreferencesViewModel = hiltViewModel()
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
        val allSelected = state.subcategories.count { it.isSelected } == state.subcategories.size

        Box(
            modifier = Modifier
                .padding(0.dp, 24.dp, 0.dp, 0.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = if (allSelected)
                        PrimaryColor
                    else
                        LightGrayColor
                )
                .background(
                    if (allSelected)
                        PrimaryColor.copy(alpha = 0.05f)
                    else
                        Color.White
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
            PreferencesSubcategoryElement(
                subcategory = subcategory,
                subcategoryIndex = index
            )
        }

        Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp))
    }
}

// Подкатегория
@Composable
fun PreferencesSubcategoryElement(
    subcategory: Subcategory,
    subcategoryIndex: Int,
    viewModel: ProductsPreferencesViewModel = hiltViewModel()
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
                color = if (subcategory.isSelected)
                    PrimaryColor
                else
                    LightGrayColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                if (subcategory.isSelected)
                    PrimaryColor.copy(alpha = 0.05f)
                else
                    Color.White
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                viewModel.selectAllProducts(subcategoryIndex)
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
                    .padding(0.dp, 16.dp, 16.dp, 0.dp)
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
        if (isExpanded) {
            PreferencesSelectAllProducts(
                subcategory = subcategory,
                onSelectAllClick = {
                    viewModel.selectAllProducts(subcategoryIndex)
                }
            )

            subcategory.products.forEachIndexed { index, product ->
                PreferencesSubcategoryProductElement(
                    product = product,
                    onProductSelected = {
                        viewModel.selectProduct(subcategoryIndex, index)
                    }
                )
            }
        }

    }
}

// Продукт подкатегории
@Composable
fun PreferencesSubcategoryProductElement(
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
                uncheckedColor = DarkGrayColor,
                checkmarkColor = Color.White,
                checkedColor = PrimaryColor
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
fun PreferencesSelectAllProducts(
    subcategory: Subcategory,
    onSelectAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    ) {
        // Чекбокс выбора всех продуктов
        Checkbox(
            modifier = Modifier
                .size(24.dp, 24.dp)
                .align(Alignment.CenterVertically),
            colors = CheckboxDefaults.colors(
                uncheckedColor = DarkGrayColor,
                checkmarkColor = Color.White,
                checkedColor = PrimaryColor
            ),
            checked = subcategory.products.count { it.isSelected } == subcategory.products.size,
            onCheckedChange = {
                onSelectAllClick()
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