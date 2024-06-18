package com.example.elephantmeal.registration_first_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.elephantmeal.R
import com.example.elephantmeal.common.presentation.ElephantMealLogo

// Превый экран регстрации
@Composable
fun RegistrationFirstScreen(
    onBackButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box {
            // Логотип приложения
            ElephantMealLogo()

            // Кнопка возврата
            Image(
                modifier = Modifier
                    .padding(24.dp, 48.dp, 0.dp, 0.dp)
                    .clip(CircleShape)
                    .clickable {
                        onBackButtonClick()
                    },
                imageVector = ImageVector.vectorResource(id = R.drawable.back_arrow),
                contentDescription = stringResource(id = R.string.back_button)
            )
        }

        // Заголовок экрана
        Text(
            modifier = Modifier
                .padding(24.dp, 58.dp, 0.dp, 0.dp),
            text = stringResource(id = R.string.registration),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}