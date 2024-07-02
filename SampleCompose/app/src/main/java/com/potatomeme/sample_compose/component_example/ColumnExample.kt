package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun ColumnPreview() {
    SampleComposeTheme {
        ColumnExample()
    }
}

@Composable
fun SimpleColumn() {
    Column {

    }
}

@Composable
fun ColumnExample() {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.size(100.dp)
    ) {
        Text(text = "첫 번째", modifier = Modifier.background(Color.Gray))
        Text(text = "두 번째", modifier = Modifier.align(Alignment.CenterHorizontally).background(Color.Gray))
        Text(text = "세 번째", modifier = Modifier.background(Color.Gray))
    }
}