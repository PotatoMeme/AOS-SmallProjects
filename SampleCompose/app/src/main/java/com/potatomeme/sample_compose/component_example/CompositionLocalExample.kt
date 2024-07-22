package com.potatomeme.sample_compose.component_example

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

// CompositionLocal
// 암식적인 데이터
// 파라미터로 데이터를 전달할수있지만 파라미터없이 데이터를 넘겨줄수있는 방법

@Preview(showBackground = true)
@Composable
fun PreviewCompositionLocal() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            CompositionLocalExam()
        }

    }
}


@Composable
fun CompositionLocalExam() {
    CompositionLocalProvider(value = LocalAbsoluteTonalElevation provides 0.dp) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(text = "LocalAbsoluteTonalElevation")
                CompositionLocalProvider(value = LocalAbsoluteTonalElevation provides 10.dp) {
                    Text(text = "test")
                    CompositionLocalProvider(value = LocalAbsoluteTonalElevation provides 10.dp) {
                        Text(text = "test")
                        CompositionLocalProvider(value = LocalAbsoluteTonalElevation provides 10.dp) {
                            Text(text = "test")
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
                        .background(Color.Black)
                        .padding((1).dp)
                )
                Text(text = "LocalContentColor")
                CompositionLocalProvider(value = LocalContentColor provides Color.Red) {
                    Text(text = "test")
                    CompositionLocalProvider(value = LocalContentColor provides Color.Green) {
                        Text(text = "test")
                        CompositionLocalProvider(value = LocalContentColor provides Color.Black) {
                            Text(text = "test")
                        }
                        Text(text = "test")
                    }
                    Text(text = "test")
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
                        .background(Color.Black)
                        .padding((1).dp)
                )
                Text(text = "LocalTextStyle")
                Text(text = "test")
                CompositionLocalProvider(value = LocalTextStyle provides TextStyle.Default) {
                    Text(text = "test")
                }
                Text(text = "test")

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
                        .background(Color.Black)
                        .padding((1).dp)
                )
            }
        }
    }
}