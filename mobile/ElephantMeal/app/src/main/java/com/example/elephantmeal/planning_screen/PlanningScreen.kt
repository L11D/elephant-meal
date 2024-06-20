package com.example.elephantmeal.planning_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elephantmeal.R
import com.example.elephantmeal.common.presentation.ElephantMealLogo
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.ui.theme.DarkGrayColor

// Экран составления плана питания
@Composable
fun PlanningScreen(
    onGetStartedClick: () -> Unit
) {
    Column {
        // Логотип приложения
        ElephantMealLogo()

        // Изображения экрана составления плана питания
        Image(
            modifier = Modifier
                .padding(48.dp, 5.dp, 48.dp, 0.dp),
            contentScale = ContentScale.Fit,
            imageVector = ImageVector.vectorResource(id = R.drawable.planning_meal_picture),
            contentDescription = stringResource(id = R.string.planning_picture)
        )

        // Заголовок экрана
        Text(
            modifier = Modifier
                .padding(24.dp, 24.dp, 24.dp, 0.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.make_meal_plan),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Описание экрана
        Text(
            modifier = Modifier
                .padding(24.dp, 24.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.planning_description),
            lineHeight = 24.sp,
            style = TextStyle(
                fontSize = 16.sp,
                color = DarkGrayColor
            )
        )

        Spacer(modifier = Modifier.weight(1f))
        
        // Кнопка Приступим
        PrimaryButton(
            topPadding = 0.dp,
            bottomPadding = 32.dp,
            text = stringResource(id = R.string.get_started),
            onClick = {
                onGetStartedClick()
            }
        )
    }
}