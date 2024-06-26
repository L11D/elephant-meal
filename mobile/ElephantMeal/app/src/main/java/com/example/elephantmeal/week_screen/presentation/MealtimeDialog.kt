package com.example.elephantmeal.week_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.day_screen.domain.Mealtime
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.LightBlueColor
import com.example.elephantmeal.ui.theme.PrimaryColor
import com.example.elephantmeal.week_screen.view_model.WeekViewModel

@Composable
fun MealtimeDialog(
    viewModel: WeekViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Dialog(
        onDismissRequest = {
            viewModel.dismissMealtimeDialog()
        }
    ) {
        Card(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp, 16.dp)
            ) {
                // Название приёма пищи
                Text(
                    text = state.dialogMealtime.name,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                // Калорийность пищи
                Row(
                    modifier = Modifier
                        .padding(0.dp, 16.dp, 0.dp, 0.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.caloric_icon),
                        contentDescription = stringResource(id = R.string.caloric_icon_description)
                    )

                    Text(
                        modifier = Modifier
                            .padding(12.dp, 0.dp),
                        text = state.dialogMealtime.caloric.toString() + " " + stringResource(id = R.string.kcal),
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = DarkGrayColor
                        )
                    )
                }

                // Рецепт
                Row(
                    modifier = Modifier
                        .padding(0.dp, 16.dp, 0.dp, 0.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.paper_icon),
                        contentDescription = stringResource(id = R.string.paper_icon_description)
                    )

                    Text(
                        modifier = Modifier
                            .padding(12.dp, 0.dp),
                        text = state.dialogMealtime.receipt,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = DarkGrayColor
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row {
                    // Время приёма пищи
                    Text(
                        text = "${state.dialogMealtime.startTime} - ${state.dialogMealtime.endTime}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LightBlueColor
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    val interactionSource = remember {
                        MutableInteractionSource()
                    }

                    // Кнопка закрытия диалога
                    Text(
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                viewModel.dismissMealtimeDialog()
                            },
                        text = stringResource(id = R.string.ok),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryColor
                        )
                    )
                }

            }
        }
    }
}