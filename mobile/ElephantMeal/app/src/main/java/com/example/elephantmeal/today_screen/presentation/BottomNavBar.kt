package com.example.elephantmeal.today_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.elephantmeal.R
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.LightBlueColor

// Нижняя навигационная панель
@Composable
fun BottomNavBar(
    isWeekModeSelected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Кнопка перехода на главный экран
        NavBarElement(
            deselectedIcon = ImageVector.vectorResource(id = R.drawable.home_icon_deselected),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.home_icon_selected),
            iconDescription = stringResource(id = R.string.home_icon_description),
            elementName = stringResource(id = R.string.homepage),
            isSelected = false
        )

        // Кнопка перехода на экран расписания рациона
        NavBarElement(
            deselectedIcon = ImageVector.vectorResource(id = R.drawable.today_icon_deselected),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.today_icon_selected),
            iconDescription = stringResource(id = R.string.today_icon_description),
            elementName = stringResource(id = R.string.today),
            isSelected = true
        )

        // Кнопка переключения в режим просмотра расписания на день
        if (isWeekModeSelected) {
            NavBarElement(
                deselectedIcon = ImageVector.vectorResource(id = R.drawable.day_icon_deselected),
                selectedIcon = ImageVector.vectorResource(id = R.drawable.day_icon_selected),
                iconDescription = stringResource(id = R.string.day_icon_description),
                elementName = stringResource(id = R.string.day),
                isSelected = false
            )
        }
        // Кнопка переключения в режим просмотра расписания на неделю
        else {
            NavBarElement(
                deselectedIcon = ImageVector.vectorResource(id = R.drawable.week_icon_deselected),
                selectedIcon = ImageVector.vectorResource(id = R.drawable.week_icon_selected),
                iconDescription = stringResource(id = R.string.week_icon_description),
                elementName = stringResource(id = R.string.week),
                isSelected = false
            )
        }

        // Кнопка перехода на экран меню
        NavBarElement(
            deselectedIcon = ImageVector.vectorResource(id = R.drawable.menu_icon_deselected),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.menu_icon_selected),
            iconDescription = stringResource(id = R.string.menu_icon_description),
            elementName = stringResource(id = R.string.menu),
            isSelected = false
        )
    }
}

// Элемент нижней навигационной панели
@Composable
fun NavBarElement(
    deselectedIcon: ImageVector,
    selectedIcon: ImageVector,
    iconDescription: String,
    elementName: String,
    isSelected: Boolean
) {
    Column {
        Box(
            modifier = Modifier
                .size(46.dp, 46.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(18.dp))
                .background(
                    if (isSelected)
                        LightBlueColor
                    else
                        Color.White
                )
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                imageVector = if (isSelected) selectedIcon else deselectedIcon,
                contentDescription = iconDescription
            )
        }

        Text(
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                .align(Alignment.CenterHorizontally),
            text = elementName,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) LightBlueColor else DarkGrayColor
            )
        )
    }
}