package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewCard() {
    SampleComposeTheme {
        CardExample()
    }
}

@Composable
fun SimpleCard() {
    Card {

    }
}

@Composable
fun CardExample() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        //shape = CardDefaults.shape,
        shape = CircleShape,
        colors = CardDefaults.cardColors(),
        //elevation = CardDefaults.cardElevation(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        border = null,
    ) {
        Column(modifier = Modifier
            .wrapContentSize()
            .padding(40.dp)) {
            repeat(5) {
                Text(text = "sample text $it")
            }
        }

    }
}