package com.example.elephantmeal.menu_screen

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.elephantmeal.menu_screen.view_model.MenuViewModel
import com.example.elephantmeal.ui.theme.PersonIconBackgroundColor
import com.example.elephantmeal.ui.theme.PrimaryColor

// Выбор фото профиля пользователя
@Composable
fun Avatar(
    viewModel: MenuViewModel= hiltViewModel()
) {
    Row {
        Box(
            modifier = Modifier
                .padding(24.dp, 24.dp, 0.dp, 0.dp)
                .size(88.dp, 88.dp)
                .clip(CircleShape)
                .background(PersonIconBackgroundColor)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.person_icon),
                contentDescription = stringResource(id = R.string.person_icon_description)
            )
        }

        val interactionSource = remember {
            MutableInteractionSource()
        }

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(24.dp, 24.dp, 0.dp, 0.dp)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    viewModel.choosePhoto()
                },
            text = stringResource(id = R.string.choose_photo),
            style = TextStyle(
                fontSize = 16.sp,
                color = PrimaryColor,
                fontWeight = FontWeight.Bold
            )
        )
    }
    
}