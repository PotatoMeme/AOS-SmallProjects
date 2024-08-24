package com.potatomeme.presentation.main

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.potatomeme.presentation.main.writing.WritingActivity
import com.potatomeme.presentation.theme.PresentationTheme

@Composable
fun MainBottomBar(
    navController: NavController,
) {
    val context= LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: MainRoute = navBackStackEntry
        ?.destination
        ?.route
        ?.let { currentRoute ->
            MainRoute.values().find { route -> route.route == currentRoute }
        } ?: MainRoute.BOARD

    MainBottomBar(
        currentRoute
    ){ newRoute: MainRoute ->
        if (newRoute == MainRoute.WRITING){
            context.startActivity(
                Intent(context,WritingActivity::class.java)
            )
        }else{
            navController.navigate(newRoute.route){
                navController.graph.startDestinationRoute?.let {
                    popUpTo(it){
                        saveState = true
                    }
                    this.launchSingleTop = true
                    this.restoreState = true
                }
            }
        }
    }

}

@Composable
private fun MainBottomBar(
    currentRoute: MainRoute,
    onItemClick: (MainRoute) -> Unit,
) {
    Column {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MainRoute.values().forEach { mainRoute ->
                IconButton(onClick = {
                    //navController.navigate(mainRoute.route)
                    onItemClick(mainRoute)
                }) {
                    Icon(
                        imageVector = mainRoute.icon,
                        contentDescription = mainRoute.contentDescription,
                        tint = if (currentRoute == mainRoute) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.White
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainBottomBarPreview() {
    PresentationTheme {
        Surface {
            val (currentRoute, setter) = remember {
                mutableStateOf(MainRoute.BOARD)
            }
            MainBottomBar(
                currentRoute
            ) { new ->
                setter(new)
            }
        }
    }
}