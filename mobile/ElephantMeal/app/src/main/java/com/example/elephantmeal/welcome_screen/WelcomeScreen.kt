package com.example.elephantmeal.welcome_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elephantmeal.R
import com.example.elephantmeal.ui.theme.GrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor

// Приветственный экран
@Composable
fun WelcomeScreen(
    onRegistrationButtonClick: () -> Unit,
    onLoginButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Логотип приложения
        Box {
            Image(
                modifier = Modifier
                    .padding(0.dp, 16.dp, 0.dp, 0.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo_image)
            )

            Text(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 24.dp)
                    .align(Alignment.BottomCenter),
                text = stringResource(id = R.string.logo_text),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 7.sp
                )
            )
        }

        // Картинка приветственного экрана
        Image(
            modifier = Modifier
                .padding(40.dp, 12.dp, 40.dp, 0.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.wecome_image),
            contentDescription = stringResource(id = R.string.welcome_image)
        )

        // Надпись Мы то, что мы едим
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 36.dp, 0.dp, 0.dp),
            text = stringResource(id = R.string.slogan),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        // Описание приложения
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 16.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.app_description),
            lineHeight = 26.sp,
            style = TextStyle(
                fontSize = 16.sp,
                color = GrayColor
            )
        )

        Spacer(Modifier.weight(1f))

        // Кнопка регистрации
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 0.dp, 24.dp, 0.dp)
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = GrayColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            onClick = {
                onRegistrationButtonClick()
            }
        ) {
            Text(
                text = stringResource(id = R.string.registration),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        }

        // Кнопка авторизации
        Button (
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 16.dp, 24.dp, 32.dp)
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor
            ),
            onClick = {
                onLoginButtonClick()
            }
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
@Preview
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onRegistrationButtonClick = {

        },
        onLoginButtonClick = {

        }
    )
}