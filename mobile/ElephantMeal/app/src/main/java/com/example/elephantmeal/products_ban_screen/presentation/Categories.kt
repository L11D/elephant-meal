package com.example.elephantmeal.products_ban_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.products_ban_screen.view_model.ProductsBanViewModel
import com.example.elephantmeal.ui.theme.DarkBlueColor
import com.example.elephantmeal.ui.theme.DeselectedCategoryColor
import com.example.elephantmeal.ui.theme.LightBlueColor


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
                .size(56.dp, 56.dp)
                .clip(RoundedCornerShape(25.dp))
                .align(Alignment.CenterHorizontally)
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
                .padding(0.dp, 16.dp, 0.dp, 0.dp)
                .align(Alignment.CenterHorizontally),
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