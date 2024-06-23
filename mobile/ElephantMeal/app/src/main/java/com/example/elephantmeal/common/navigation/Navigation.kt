package com.example.elephantmeal.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elephantmeal.cheat_meal_screen.CheatMealScreen
import com.example.elephantmeal.login_screen.presentation.LoginScreen
import com.example.elephantmeal.menu_screen.MenuScreen
import com.example.elephantmeal.plan_choose_screen.presentation.PlanChooseScreen
import com.example.elephantmeal.planning_screen.PlanningScreen
import com.example.elephantmeal.products_ban_screen.presentation.ProductsBanScreen
import com.example.elephantmeal.products_preferences_screen.presentation.ProductsPreferencesScreen
import com.example.elephantmeal.registration_first_screen.presentation.RegistrationFirstScreen
import com.example.elephantmeal.registration_second_screen.presentation.RegistrationSecondScreen
import com.example.elephantmeal.registration_third_screen.presentation.RegistrationThirdScreen
import com.example.elephantmeal.today_screen.presentation.TodayScreen
import com.example.elephantmeal.welcome_screen.WelcomeScreen

@Composable
fun ElephantMealNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
       // startDestination = Screen.WelcomeScreen.name
        startDestination = Screen.MenuScreen.name
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
                    navController.navigate(Screen.PlanningScreen.name) {
                        popUpTo(Screen.WelcomeScreen.name) {
                            inclusive = true
                        }
                    }
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
            RegistrationThirdScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                },

                onLoginButtonClick = {
                    navController.navigate(Screen.LoginScreen.name) {
                        popUpTo(Screen.WelcomeScreen.name)
                    }
                },

                onRegistered = {
                    navController.navigate(Screen.PlanningScreen.name) {
                        popUpTo(Screen.WelcomeScreen.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Экран составления плана питания
        composable(Screen.PlanningScreen.name) {
            PlanningScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.PlanChooseScreen.name)
                }
            )
        }

        // Экран выбора плана питания
        composable(Screen.PlanChooseScreen.name) {
            PlanChooseScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                },

                onContinue = {
                    navController.navigate(Screen.CheatMealScreen.name)
                }
            )
        }

        // Экран настройки чит мила
        composable(Screen.CheatMealScreen.name) {
            CheatMealScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                },

                onContinue = {
                    navController.navigate(Screen.ProductsBanScreen.name)
                }
            )
        }

        // Экран указания непредпочитаемых продуктов
        composable(Screen.ProductsBanScreen.name) {
            ProductsBanScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                },

                onContinue = {
                    navController.navigate(Screen.ProductsPreferencesScreen.name)
                }
            )
        }

        // Экран указания предпочитаемых продуктов
        composable(Screen.ProductsPreferencesScreen.name) {
            ProductsPreferencesScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                },

                onContinue = {
                    navController.navigate(Screen.TodayScreen.name)
                }
            )
        }

        // Экран расписания рациона на день
        composable(Screen.TodayScreen.name) {
            TodayScreen(
                onHomeClick = {

                },

                onDayClick = {

                },

                onWeekClick = {

                },

                onMenuClick = {
                    navController.navigate(Screen.MenuScreen.name) {
                        popUpTo(Screen.TodayScreen.name)
                    }
                }
            )
        }

        // Экран меню
        composable(Screen.MenuScreen.name) {
            MenuScreen()
        }
    }
}