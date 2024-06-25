package com.example.elephantmeal.menu_screen.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.elephantmeal.R
import com.example.elephantmeal.ui.theme.PrimaryColor

// Диалоговое окно выбора фотографии
@Composable
fun ChoosePhotoDialog(
    onDismissRequest: () -> Unit,
    onChoosePhoto: () -> Unit,
    onTakePhoto: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp, 16.dp, 16.dp, 0.dp),
                    text = stringResource(id = R.string.choose_photo_text),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .padding(16.dp, 0.dp, 16.dp, 16.dp)
                        .fillMaxWidth()
                ) {
                   Button(
                       modifier = Modifier
                           .padding(0.dp, 0.dp, 16.dp, 0.dp)
                           .weight(1f),
                       onClick = {
                           onChoosePhoto()
                       },
                       colors = ButtonDefaults.buttonColors(
                           containerColor = PrimaryColor
                       )
                   ) {
                       Text(
                           text = stringResource(id = R.string.choose),
                           style = TextStyle(
                               fontSize = 16.sp,
                               color = Color.White
                           )
                       )
                   }

                    Button(
                        modifier = Modifier
                            .padding(16.dp, 0.dp, 0.dp, 0.dp)
                            .weight(1f),
                        onClick = {
                            onTakePhoto()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryColor
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.take),
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}