package com.potatomeme.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.potatomeme.domain.usecase.login.SignUpUseCase

@Composable
fun LoginNavHost() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginRoute.WelcomeScreen.name,
    ) {
        composable(route = LoginRoute.WelcomeScreen.name) {
            WelcomeScreen {
                navController.navigate(LoginRoute.LoginScreen.name)
            }
        }
        composable(route = LoginRoute.LoginScreen.name) {
            LoginScreen(
                onSignUpClick = {
                    navController.navigate(LoginRoute.SignUpScreen.name)
                }
            )
        }
        composable(route = LoginRoute.SignUpScreen.name) {
            SignUpScreen(
                onNavigateToLoginScreen = {
                    navController.navigate(
                        route = LoginRoute.LoginScreen.name,
                        navOptions = navOptions {
                            popUpTo(LoginRoute.WelcomeScreen.name)
                        }
                    )
                }
            )
        }
    }
}