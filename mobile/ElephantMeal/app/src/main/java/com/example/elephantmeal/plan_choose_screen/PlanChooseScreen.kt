package com.example.elephantmeal.plan_choose_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.elephantmeal.common.presentation.PrimaryButton
import com.example.elephantmeal.plan_choose_screen.view_model.PlanChooseEvent
import com.example.elephantmeal.plan_choose_screen.view_model.PlanChooseViewModel
import com.example.elephantmeal.registration_second_screen.presentation.LogoWithBackButton
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.LightGrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor
import kotlinx.coroutines.flow.collect

// Экран составления плана питания
@Composable
fun PlanChooseScreen(
    onBackButtonClick: () -> Unit,
    onContinue: () -> Unit,
    viewModel: PlanChooseViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is PlanChooseEvent.Continue -> {
                    onContinue()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
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
                .padding(24.dp, 62.dp, 24.dp, 16.dp),
            text = stringResource(id = R.string.choose_plan),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Планы питания
        state.mealPlans.forEachIndexed { index, plan ->
            MealPlanElement(
                planName = plan.name,
                planDescription = plan.description,
                isSelected = index == state.selectedIndex,
                onPlanSelected = {
                    viewModel.onMeanPlanSelected(index)
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка продолжения
        PrimaryButton(
            topPadding = 16.dp,
            bottomPadding = 32.dp,
            text = stringResource(id = R.string.continuation),
            isEnabled = state.isContinueButtonEnabled,
            onClick = {
                viewModel.onContinuteButtonClick()
            }
        )
    }
}

// План питания
@Composable
fun MealPlanElement(
    planName: String,
    planDescription: String,
    isSelected: Boolean,
    onPlanSelected: () -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .padding(24.dp, 16.dp, 24.dp, 0.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = if (isSelected)
                    PrimaryColor
                else
                    LightGrayColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                if (isSelected)
                    PrimaryColor.copy(alpha = 0.05f)
                else
                    Color.White
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onPlanSelected()
            }
    ) {
        Row {
            // Название плана питания
            Text(
                modifier = Modifier
                    .padding(16.dp, 16.dp, 0.dp, 16.dp)
                    .weight(1f),
                text = planName,
                style = TextStyle(
                    fontSize = 24.sp
                )
            )

            // Кнопка раскрытия описания
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

        // Описание плана питания
        if (isExpanded) {
            Text(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 52.dp, 16.dp),
                text = planDescription,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = DarkGrayColor
                )
            )
        }
    }
}