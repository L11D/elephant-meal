package com.example.elephantmeal.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.elephantmeal.cheat_meal_screen.CheatMealScreen
import com.example.elephantmeal.confirm_screen.presentation.ConfirmScreen
import com.example.elephantmeal.login_screen.presentation.LoginScreen
import com.example.elephantmeal.menu_screen.presentation.MenuWithCamera
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
        startDestination = Screen.WelcomeScreen.name
       // startDestination = Screen.ProductsBanScreen.name
       // startDestination = "${Screen.TodayScreen.name}/false"
        //startDestination = Screen.ConfirmScreen.name
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

        // Экран подтверждения регистрации
        composable(Screen.ConfirmScreen.name) {
            ConfirmScreen(
                onConfirm = {
                    navController.navigate(Screen.LoginScreen.name) {
                        popUpTo(Screen.ConfirmScreen.name) {
                            inclusive = true
                        }
                    }
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

                onContinueButtonClick = { surname, name, lastName, email ->
                    navController.navigate(
                        "${Screen.RegistrationSecondScreen.name}/" +
                                "$surname/$name/$lastName/$email"
                    )
                }
            )
        }

        // Второй экран регистрации
        composable(
            route = "${Screen.RegistrationSecondScreen.name}/" +
                    "{${NavParams.SURNAME}}/" +
                    "{${NavParams.NAME}}/" +
                    "{${NavParams.LAST_NAME}}/" +
                    "{${NavParams.EMAIL}}",
            arguments = listOf(
                navArgument(NavParams.SURNAME) {type = NavType.StringType},
                navArgument(NavParams.NAME) {type = NavType.StringType},
                navArgument(NavParams.LAST_NAME) {type = NavType.StringType},
                navArgument(NavParams.EMAIL) {type = NavType.StringType},
            )
        ) { backStackEntry ->
            val surname = backStackEntry.arguments?.getString(NavParams.SURNAME)
            val name = backStackEntry.arguments?.getString(NavParams.NAME)
            val lastName = backStackEntry.arguments?.getString(NavParams.LAST_NAME)
            val email = backStackEntry.arguments?.getString(NavParams.EMAIL)

            RegistrationSecondScreen(
                surname = surname ?: "",
                name = name ?: "",
                lastName = lastName ?: "",
                email = email ?: "",

                onBackButtonClick = {
                    navController.popBackStack()
                },

                onLoginButtonClick = {
                    navController.navigate(Screen.LoginScreen.name) {
                        popUpTo(Screen.WelcomeScreen.name)
                    }
                },

                onContinueButtonClick = { surname, name, lastName, email, gender, weight, height,
                    birthDate ->
                    navController.navigate(
                        "${Screen.RegistrationThirdScreen.name}/" +
                                "$surname/$name/$lastName/$email/$gender/$weight/$height/$birthDate"
                    )
                }
            )
        }

        // Третий экран регистрации
        composable(
            route = "${Screen.RegistrationThirdScreen.name}/" +
                    "{${NavParams.SURNAME}}/" +
                    "{${NavParams.NAME}}/" +
                    "{${NavParams.LAST_NAME}}/" +
                    "{${NavParams.EMAIL}}/" +
                    "{${NavParams.GENDER}}/" +
                    "{${NavParams.WEIGHT}}/" +
                    "{${NavParams.HEIGHT}}/" +
                    "{${NavParams.BIRTH_DATE}}",
            arguments = listOf(
                navArgument(NavParams.SURNAME) { type = NavType.StringType },
                navArgument(NavParams.NAME) { type = NavType.StringType },
                navArgument(NavParams.LAST_NAME) { type = NavType.StringType },
                navArgument(NavParams.EMAIL) { type = NavType.StringType },
                navArgument(NavParams.GENDER) { type = NavType.IntType },
                navArgument(NavParams.WEIGHT) { type = NavType.FloatType },
                navArgument(NavParams.HEIGHT) { type = NavType.FloatType },
                navArgument(NavParams.BIRTH_DATE) { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val surname = backStackEntry.arguments?.getString(NavParams.SURNAME) ?: ""
            val name = backStackEntry.arguments?.getString(NavParams.NAME) ?: ""
            val lastName = backStackEntry.arguments?.getString(NavParams.LAST_NAME) ?: ""
            val email = backStackEntry.arguments?.getString(NavParams.EMAIL) ?: ""
            val gender = backStackEntry.arguments?.getInt(NavParams.GENDER)
            val weight = backStackEntry.arguments?.getFloat(NavParams.WEIGHT)
            val height = backStackEntry.arguments?.getFloat(NavParams.HEIGHT)
            val birthDate = backStackEntry.arguments?.getString(NavParams.BIRTH_DATE)

            RegistrationThirdScreen(
                surname = surname,
                name = name,
                lastName = lastName,
                email = email,
                gender = gender,
                weight = weight,
                height = height,
                birthDate = birthDate,

                onBackButtonClick = {
                    navController.popBackStack()
                },

                onLoginButtonClick = {
                    navController.navigate(Screen.LoginScreen.name) {
                        popUpTo(Screen.WelcomeScreen.name)
                    }
                },

                onRegistered = {
                    navController.navigate(Screen.ConfirmScreen.name) {
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
                    navController.navigate("${Screen.TodayScreen.name}/true")
                }
            )
        }

        // Экран расписания рациона
        composable(
            route = "${Screen.TodayScreen.name}/{${NavParams.IS_WEEK_MODE_SELECTED}}",
            arguments = listOf(navArgument(NavParams.IS_WEEK_MODE_SELECTED) { type = NavType.BoolType})
        ) { backStackEntry ->
            val isWeekModeSelected = backStackEntry.arguments?.getBoolean(NavParams.IS_WEEK_MODE_SELECTED)

            TodayScreen(
                onHomeClick = {

                },

                onMenuClick = { weekMode ->
                    navController.navigate("${Screen.MenuScreen.name}/${weekMode}")
                },

                isWeekModeSelected = isWeekModeSelected ?: false
            )
        }

        // Экран меню
        composable(
            route = "${Screen.MenuScreen.name}/{${NavParams.IS_WEEK_MODE_SELECTED}}",
            arguments = listOf(navArgument(NavParams.IS_WEEK_MODE_SELECTED) {type = NavType.BoolType})
        ) { backStackEntry ->
            val isWeekModeSelected = backStackEntry.arguments?.getBoolean(NavParams.IS_WEEK_MODE_SELECTED)

            MenuWithCamera(
                onLogout = {
                    navController.navigate(Screen.WelcomeScreen.name) {
                        popUpTo("${Screen.TodayScreen.name}/{${NavParams.IS_WEEK_MODE_SELECTED}}") {
                            inclusive = true
                        }
                    }
                },

                onTodayClick = { weekMode ->
                    navController.navigate("${Screen.TodayScreen.name}/$weekMode") {
                        popUpTo("${Screen.TodayScreen.name}/{${NavParams.IS_WEEK_MODE_SELECTED}}") {
                            inclusive = true
                        }
                    }
                },

                onDayClick = {
                    navController.navigate("${Screen.TodayScreen.name}/false") {
                        popUpTo("${Screen.TodayScreen.name}/{${NavParams.IS_WEEK_MODE_SELECTED}}") {
                            inclusive = true
                        }
                    }
                },

                onWeekClick = {
                    navController.navigate("${Screen.TodayScreen.name}/true") {
                        popUpTo("${Screen.TodayScreen.name}/{${NavParams.IS_WEEK_MODE_SELECTED}}") {
                            inclusive = true
                        }
                    }
                },

                isWeekSelected = isWeekModeSelected ?: false
            )
        }
    }
}