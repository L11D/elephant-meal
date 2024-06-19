package com.example.elephantmeal.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elephantmeal.login_screen.presentation.LoginScreen
import com.example.elephantmeal.registration_first_screen.presentation.RegistrationFirstScreen
import com.example.elephantmeal.registration_second_screen.presentation.RegistrationSecondScreen
import com.example.elephantmeal.registration_third_screen.RegistrationThirdScreen
import com.example.elephantmeal.welcome_screen.WelcomeScreen

@Composable
fun ElephantMealNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.WelcomeScreen.name
      //  startDestination = Screen.RegistrationSecondScreen.name
    ) {
        // Приветственный экран
        composable(Screen.WelcomeScreen.name) {
            WelcomeScreen(
                onRegistrationButtonClick = {
                    navController.navigate(Screen.RegistrationFirstScreen.name)
                },

                onLoginButtonClick = {
                    navController.navigate(Screen.LoginScreen.name)
                }
            )
        }

        // Экран входа
        composable(Screen.LoginScreen.name) {
            LoginScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                },

                onRegisterButtonClick = {
                    navController.navigate(Screen.RegistrationFirstScreen.name) {
                        popUpTo(Screen.LoginScreen.name) {
                            inclusive = true
                        }
                    }
                },

                onLogin = {

                }
            )
        }

        // Первый экран регистрации
        composable(Screen.RegistrationFirstScreen.name) {
            RegistrationFirstScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                },

                onLoginButtonClick = {
                    navController.navigate(Screen.LoginScreen.name) {
                        popUpTo(Screen.RegistrationFirstScreen.name) {
                            inclusive = true
                        }
                    }
                },

                onContinueButtonClick = {
                    navController.navigate(Screen.RegistrationSecondScreen.name)
                }
            )
        }

        // Второй экран регистрации
        composable(Screen.RegistrationSecondScreen.name) {
            RegistrationSecondScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                },

                onLoginButtonClick = {
                    navController.navigate(Screen.LoginScreen.name) {
                        popUpTo(Screen.WelcomeScreen.name)
                    }
                },

                onContinueButtonClick = {
                    navController.navigate(Screen.RegistrationThirdScreen.name)
                }
            )
        }

        // Третий экран регистрации
        composable(Screen.RegistrationThirdScreen.name) {
            RegistrationThirdScreen()
        }
    }
}