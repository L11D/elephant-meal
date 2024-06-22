package com.example.elephantmeal.products_ban_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.common.presentation.SearchField
import com.example.elephantmeal.products_ban_screen.view_model.ProductsBanViewModel
import com.example.elephantmeal.registration_second_screen.presentation.LogoWithBackButton

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