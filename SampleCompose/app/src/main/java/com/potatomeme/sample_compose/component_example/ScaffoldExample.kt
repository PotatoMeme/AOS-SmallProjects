package com.potatomeme.sample_compose.component_example

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults.contentWindowInsets
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewScaffold() {
    SampleComposeTheme {
        ScaffoldExample()
    }
}

@Composable
fun SimpleScaffold() {
    Scaffold { paddingValues: PaddingValues ->
        Log.d("", "SimpleScaffold: ${paddingValues}")
        Row {
            repeat(5) {
                Text(text = "sample $it")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample(isFirst: Boolean = true) {
    Scaffold(
        //modifier: Modifier = Modifier,
        modifier = Modifier.fillMaxSize(),
        //topBar: @Composable () -> Unit = {},
        topBar = @Composable {
            TopAppBar(
                //title: @Composable () -> Unit,
                title = @Composable {
                    Text(modifier = Modifier.background(Color.White), text = "test")
                },
                //modifier: Modifier = Modifier,
                modifier = Modifier,
                //navigationIcon: @Composable () -> Unit = {},
                navigationIcon = @Composable {
                    if (!isFirst) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "뒤로 가기"
                            )
                        }
                    }
                },
                //actions: @Composable RowScope.() -> Unit = {},
                actions = {
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    /*Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")
                    Text(modifier = Modifier.background(Color.White), text = "test")*/
                },
                //windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
                windowInsets = TopAppBarDefaults.windowInsets,
                //colors: TopAppBarColors = TopAppBarDefaults . topAppBarColors (),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red),
                //scrollBehavior: TopAppBarScrollBehavior? = null
                scrollBehavior = null
            )
        },
        //bottomBar: @Composable () -> Unit = {},
        bottomBar = @Composable {
            BottomAppBar(
                //modifier: Modifier = Modifier,
                modifier = Modifier,
                //containerColor: Color = BottomAppBarDefaults.containerColor,
                containerColor = Color.Blue,
                //contentColor: Color = contentColorFor(containerColor),
                //tonalElevation: Dp = BottomAppBarDefaults.ContainerElevation,
                //contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding,
                //windowInsets: WindowInsets = BottomAppBarDefaults.windowInsets,
                //content: @Composable RowScope.() -> Unit
                content = {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.weight(1.0f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "뒤로 가기"
                            )
                        }
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.weight(1.0f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "뒤로 가기"
                            )
                        }
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.weight(1.0f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = "뒤로 가기"
                            )
                        }
                    }
                },
            )
        },
        //snackbarHost: @Composable () -> Unit = {},
        snackbarHost = @Composable {
               Text(modifier = Modifier
                   .fillMaxWidth()
                   .background(Color.Gray),text = "test")
        },
        //floatingActionButton: @Composable () -> Unit = {},
        floatingActionButton = @Composable {
               FloatingActionButton(onClick = { /*TODO*/ }) {
                   Icon(
                       imageVector = Icons.Filled.Favorite,
                       contentDescription = "좋아요"
                   )
               }
        },
        //floatingActionButtonPosition: FabPosition = FabPosition.End,
        floatingActionButtonPosition = FabPosition.Center,
        //containerColor: Color = MaterialTheme.colorScheme.background,
        containerColor = MaterialTheme.colorScheme.background,
        //contentColor: Color = contentColorFor(containerColor),
        contentColor = contentColorFor(Color.Black),
        //contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
        contentWindowInsets = contentWindowInsets,
        //content: @Composable (PaddingValues) -> Unit
        content = @Composable { paddingValues: PaddingValues ->
            Surface(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                Text(modifier = Modifier.background(Color.White), text = "test")
            }
        },
    )
}