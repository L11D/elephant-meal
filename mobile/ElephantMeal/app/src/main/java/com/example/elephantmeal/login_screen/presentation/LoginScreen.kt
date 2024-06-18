package com.example.elephantmeal.login_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.elephantmeal.common.presentation.ElephantMealLogo
import com.example.elephantmeal.common.presentation.InputField
import com.example.elephantmeal.common.presentation.PasswordInputField
import com.example.elephantmeal.login_screen.view_model.LoginViewModel
import com.example.elephantmeal.ui.theme.DisabledButtonColor
import com.example.elephantmeal.ui.theme.GrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor

// Экран входа
@Composable
fun LoginScreen(
    onBackButtonClick: () -> Unit,
    onRegisterButtonClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
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
            text = stringResource(id = R.string.login_screen_name),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Поле ввода электронной почты
        InputField(
            label = stringResource(id = R.string.email),
            topPadding = 48.dp,
            value = viewModel.email,
            onValueChange = { viewModel.onEmailChange(it) }
        )

        // Поле ввода пароля
        PasswordInputField(
            label = stringResource(id = R.string.password),
            topPadding = 16.dp,
            value = viewModel.password,
            isVisible = state.isPasswordVisible,
            onValueChange = { viewModel.onPasswordChange(it) },
            onPasswordVisibilityChange = { viewModel.onShowPasswordButtonClick() }
        )

        // Восстановление пароля
        val interactionSource = remember {
            MutableInteractionSource()
        }

        Text(
            modifier = Modifier
                .padding(0.dp, 16.dp, 24.dp, 0.dp)
                .align(Alignment.End)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    viewModel.onResetPasswordClick()
                },
            text = stringResource(id = R.string.forgot_password),
            style = TextStyle(
                fontSize = 14.sp,
                color = PrimaryColor
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка входа
        Button(
            modifier = Modifier
                .padding(24.dp, 0.dp, 24.dp, 0.dp)
                .height(56.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            enabled = state.isLoginEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                disabledContainerColor = DisabledButtonColor
            ),
            onClick = { /*TODO*/ }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = stringResource(id = R.string.login),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
        }

        // Регистрация
        Row(
            modifier = Modifier
                .padding(0.dp, 20.dp, 0.dp, 32.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.have_no_account) + " ",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = GrayColor
                )
            )

            Text(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onRegisterButtonClick()
                    },
                text = stringResource(id = R.string.create_account),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = PrimaryColor
                )
            )
        }
    }
}