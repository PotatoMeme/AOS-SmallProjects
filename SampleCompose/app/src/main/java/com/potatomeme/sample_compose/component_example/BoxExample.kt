package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewBox(){
    SampleComposeTheme {
        BoxExample()
    }
}

@Composable
fun SimpleBox(){
    Box {

    }
}

@Composable
fun BoxExample(){
    Box(
        modifier = Modifier.background(Color.White).padding(20.dp),
        // contentAlignment = Alignment.Center,
        //들어오는 최소 제약 조건을 콘텐츠에 전달해야 하는지 여부.
        propagateMinConstraints = false,
    ) {
        Box(
            modifier = Modifier
                .background(Color.Cyan)
                .fillMaxSize()
                .align(Alignment.BottomEnd)
        )
        Box(
            modifier = Modifier
                .background(Color.DarkGray)
                .size(70.dp)
                .align(Alignment.CenterStart)
        )
    }
}