package com.potatomeme.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.potatomeme.navigation.ui.theme.SampleComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

var _i = 0
@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, "Home", modifier = Modifier) {
        composable("Home") {
            Column {
                Text("Home")
                Button(onClick = {
                    navController.navigate("Office") {
//                        popUpTo("Home") {
//                            inclusive = true
//                        }
                    }
                }) {
                    Text("Office로 이동")
                }
                Button(onClick = {
                    navController.navigate("Playground") {
//                        popUpTo("Home") {
//                            inclusive = true
//                        }
                    }
                }) {
                    Text("PlayGround로 이동")
                }
            }
        }
        composable("Office") {
            Column {
                Text("Office")
                Button(onClick = {
                    navController.navigate("Playground") {
//                        popUpTo("Home") {
//                            inclusive = true
//                        }
                    }
                }) {
                    Text("Playground 이동")
                }
                Button(onClick = {
                    navController.navigate("Home") {
//                        popUpTo("Home") {
//                            inclusive = true
//                        }
                    }
                }) {
                    Text("Home으로 이동")
                }
            }
        }
        composable("Playground") {
            Column {
                Text("PlayGround")
                Button(onClick = {
                    navController.navigate("Office") {
//                        popUpTo("Home") {
//                            inclusive = true
//                        }
                    }
                }) {
                    Text("Office로 이동")
                }
                Button(onClick = {
                    navController.navigate("Home") {
//                        popUpTo("Home") {
//                            inclusive = true
//                        }
                    }
                }) {
                    Text("Home으로 이동")
                }
                Button(onClick = {
                    navController.navigate("Argument/fastcampus") {
//                        popUpTo("Home") {
//                            inclusive = true
//                        }
                    }
                }) {
                    Text("Argument로 이동")
                }
            }
        }


        composable("Argument/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            Text("userId: $userId")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SampleComposeTheme {
        Greeting()
    }
}