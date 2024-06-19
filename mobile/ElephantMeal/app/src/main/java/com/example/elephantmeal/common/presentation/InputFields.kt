package com.example.elephantmeal.common.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elephantmeal.R
import com.example.elephantmeal.ui.theme.DarkGrayColor
import com.example.elephantmeal.ui.theme.ErrorColor
import com.example.elephantmeal.ui.theme.LightGrayColor
import com.example.elephantmeal.ui.theme.PrimaryColor

// Поле ввода
@Composable
fun InputField(
    label: String,
    topPadding: Dp,
    value: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(8.dp, topPadding, 8.dp, 0.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            shape = RoundedCornerShape(8.dp),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    text = label,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                disabledBorderColor = Color.White,
                errorBorderColor = Color.White,
                focusedBorderColor = Color.White,
                focusedLabelColor = DarkGrayColor,
                unfocusedLabelColor = DarkGrayColor,
                disabledLabelColor = DarkGrayColor
            ),
        )

        Box(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(
                    if (isError)
                        ErrorColor
                    else if (isFocused)
                        PrimaryColor
                    else
                        LightGrayColor
                )
                .align(Alignment.BottomCenter)
        )
    }
}

// Поле ввода пароля
@Composable
fun PasswordInputField(
    isVisible: Boolean,
    label: String,
    topPadding: Dp,
    value: String,
    onValueChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(8.dp, topPadding, 8.dp, 0.dp)
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                shape = RoundedCornerShape(8.dp),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                },
                visualTransformation = if (isVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White,
                    disabledBorderColor = Color.White,
                    errorBorderColor = Color.White,
                    focusedBorderColor = Color.White,
                    focusedLabelColor = DarkGrayColor,
                    unfocusedLabelColor = DarkGrayColor,
                    disabledLabelColor = DarkGrayColor
                ),
            )

            Image(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 16.dp, 12.dp)
                    .align(Alignment.Bottom)
                    .clip(CircleShape)
                    .clickable {
                        onPasswordVisibilityChange()
                    },
                imageVector = ImageVector.vectorResource(id = if (isVisible)
                    R.drawable.closed_eye
                else
                    R.drawable.opened_eye),
                contentDescription = stringResource(id = if (isVisible)
                    R.string.closed_eye_description
                else
                    R.string.opened_eye_description)
            )
        }


        Box(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(
                    if (isFocused) PrimaryColor else LightGrayColor
                )
                .align(Alignment.BottomCenter)
        )
    }
}