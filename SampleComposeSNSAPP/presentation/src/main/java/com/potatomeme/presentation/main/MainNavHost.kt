package com.potatomeme.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.potatomeme.presentation.main.board.BoardScreen
import com.potatomeme.presentation.main.setting.SettingScreen
import com.potatomeme.presentation.theme.PresentationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    title = {
                        Text(text = "Connected")
                    },
                )
            },

            content = { contentPadding ->
                NavHost(
                    modifier = Modifier.padding(contentPadding),
                    navController = navController,
                    startDestination = MainRoute.BOARD.route
                ) {
                    composable(route = MainRoute.BOARD.route){
                        BoardScreen()
                    }
                    composable(route = MainRoute.SETTING.route){
                        SettingScreen()
                    }
                }
            },

            bottomBar = {
                MainBottomBar(navController = navController)
            }
        )
    }
}

@Preview
@Composable
fun MainNavHostPreview() {
    PresentationTheme {
        MainNavHost()
    }
}