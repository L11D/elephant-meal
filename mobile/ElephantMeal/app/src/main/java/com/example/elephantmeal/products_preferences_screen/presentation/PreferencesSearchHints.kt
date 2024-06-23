package com.example.elephantmeal.products_preferences_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.products_ban_screen.domain.Product
import com.example.elephantmeal.products_ban_screen.view_model.ProductsBanViewModel
import com.example.elephantmeal.products_preferences_screen.view_model.ProductsPreferencesViewModel
import com.example.elephantmeal.ui.theme.LightBlueColor
import com.example.elephantmeal.ui.theme.PrimaryColor

// Подсказки при поиске
@Composable
fun PreferencesSearchHints(
    viewModel: ProductsPreferencesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .padding(24.dp, 0.dp, 24.dp, 0.dp)
            .fillMaxWidth()
            .heightIn(0.dp, 122.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(LightBlueColor)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        // Выбор всех предложенных продуктов
        PreferencesSelectAllHints()

        state.hints.forEachIndexed { index, product ->
            PreferencesSearchHintElement(
                product = product,
                onProductSelected = {
                    viewModel.selectHint(product)
                }
            )
        }
    }
}

// Выбор всех предложенных продуктов
@Composable
fun PreferencesSelectAllHints(
    viewModel: ProductsPreferencesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 8.dp)
        ) {
            // Чекбокс выбора продукта
            Checkbox(
                modifier = Modifier
                    .size(24.dp, 24.dp)
                    .align(Alignment.CenterVertically),
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White,
                    checkedColor = PrimaryColor
                ),
                checked = state.hints.count { it.isSelected } == state.hints.size,
                onCheckedChange = {
                    viewModel.selectAllHints()
                }
            )

            // Название продукта
            Text(
                modifier = Modifier
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
                    .align(Alignment.CenterVertically),
                text = stringResource(id = R.string.select_all),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            )
        }
    }
}

// Предложенный продукт
@Composable
fun PreferencesSearchHintElement(
    product: Product,
    onProductSelected: () -> Unit
) {
    Column {
        Divider(color = Color.White)

        Row(
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 8.dp)
        ) {
            // Чекбокс выбора продукта
            Checkbox(
                modifier = Modifier
                    .size(24.dp, 24.dp)
                    .align(Alignment.CenterVertically),
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.White,
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
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
                    .align(Alignment.CenterVertically),
                text = product.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            )
        }
    }
}