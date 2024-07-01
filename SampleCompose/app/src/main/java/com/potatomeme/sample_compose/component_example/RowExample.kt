package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
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
fun RowPreview() {
    SampleComposeTheme {
        RowExample()
    }
}

@Composable
fun SimpleRow() {
    Row {

    }
}

@Composable
fun RowExample() {
    Row(
        modifier = Modifier
            .height(40.dp)
            .width(200.dp)
            .background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically,
        //레이아웃의 자식들의 가로 배치.
        //참고 :https://kotlinworld.com/189
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "첫 번째!", modifier = Modifier
                //.weight(3f)
                .background(Color.Magenta)
        )
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",
            modifier = Modifier
                //.weight(2f)
                .background(Color.Cyan)
        )
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",
            modifier = Modifier
                //.weight(2f)
                .background(Color.Cyan)
        )
        Text(text = "세 번째! ", modifier = Modifier
            //.weight(3f)
            .background(Color.Yellow)
        )
    }

}