package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewBottomAppBar() {
    SampleComposeTheme {
        SampleBottomAppBar()
    }
}

@Composable
fun SampleBottomAppBar() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
            ) {
                Text("헬로")
                Button(
                    onClick = {

                    }
                ) {
                    Text("A")
                }
                Button(
                    onClick = {

                    }
                ) {
                    Text("B")
                }
                Button(
                    onClick = {

                    }
                ) {
                    Text("C")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("빼기")
        }
    }
}