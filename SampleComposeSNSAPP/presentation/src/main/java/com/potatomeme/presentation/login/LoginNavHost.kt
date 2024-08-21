package com.potatomeme.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
            LoginScreen()
        }
        composable(route = LoginRoute.SignUpScreen.name) {
            SignUpScreen(
                id = "",
                username = "",
                password = "",
                checkPassword = "",
                onIdChange = {},
                onUsernameChange = {},
                onPasswordChange = {},
                onCheckPasswordChange = {},
                onSignUpClick = {}
            )
        }
    }
}