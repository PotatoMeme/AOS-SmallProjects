package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewRequestFocus() {
    SampleComposeTheme {
        SampleFocusRequest()
    }
}

@Composable
fun SampleFocusRequest() {

    val text = remember {
        mutableStateOf("")
    }

    val focusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .focusRequester(focusRequester),
                    value = text.component1(),
                    onValueChange = text.component2(),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = { focusRequester.requestFocus() }) {
                    Text(text = "focus to textField")
                }
            }
        }
    }
}