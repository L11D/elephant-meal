package com.example.elephantmeal.camera_screen.presentation

import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.elephantmeal.R
import com.example.elephantmeal.camera_screen.view_model.CameraEvent
import com.example.elephantmeal.camera_screen.view_model.CameraViewModel
import kotlinx.coroutines.flow.collect

// Экран камеры
@Composable
fun CameraScreen(
    onCameraClosed: () -> Unit,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val applicationContext = LocalContext.current.applicationContext
    val context = LocalContext.current

    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is CameraEvent.CloseCamera -> {
                    controller.unbind()
                    onCameraClosed()
                }
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Изображение с камеры
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            }
        )

        Row(
            modifier = Modifier
                .height(124.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val interactionSource = remember {
                MutableInteractionSource()
            }

            // Разворот камеры
            Image(
                modifier = Modifier
                    .padding(24.dp, 0.dp, 0.dp, 0.dp)
                    .align(Alignment.CenterVertically)
                    .size(24.dp, 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
                        controller.cameraSelector =
                            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            } else {
                                CameraSelector.DEFAULT_BACK_CAMERA
                            }
                    },
                imageVector = ImageVector.vectorResource(id = R.drawable.camera_switch),
                contentDescription = stringResource(id = R.string.camera_switch_description)
            )

            // Фотографирование
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(32.dp, 32.dp)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
                        viewModel.takePhoto(controller, context)
                    },
                imageVector = ImageVector.vectorResource(id = R.drawable.take_photo),
                contentDescription = stringResource(id = R.string.take_photo_description)
            )

            // Закрытие камеры
            Image(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 24.dp, 0.dp)
                    .align(Alignment.CenterVertically)
                    .size(24.dp, 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
                        viewModel.closeCamera()
                    },
                imageVector = ImageVector.vectorResource(id = R.drawable.camera_back_arrow),
                contentDescription = stringResource(id = R.string.camera_back_arrow)
            )
        }
    }

}