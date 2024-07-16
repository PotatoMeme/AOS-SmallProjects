package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewState() {
    SampleComposeTheme {
        StateExample()
    }
}


@Composable
fun StateExample() {
    val (pyeongGetter,pyeongSetter) = remember {
        mutableStateOf("23")
    }
    val (squaremeterGetter,squaremeterSetter) = remember {
        mutableStateOf((23 * 3.306).toString())
    }

    StateHoistingExample(
        pyeong = pyeongGetter,
        squaremeter = squaremeterGetter,
        onStateChange = {
            if (it.isBlank()) {
                pyeongSetter("")
                squaremeterSetter("")
                return@StateHoistingExample
            }
            val numericValue = it.toFloatOrNull() ?: return@StateHoistingExample
            pyeongSetter(numericValue.toString())
            val sqm = numericValue * 3.306
            squaremeterSetter(sqm.toString())
        }
    )
}

@Composable
fun StateHoistingExample(// State Hoisting: Stateful한 컴포저블을 Stateless 하도록 만들기 위한 디자인 패턴
    pyeong: String,
    squaremeter: String,
    onStateChange: (String) -> Unit,
) {//StateLess하게 만들어 상태가없어 테스트가쉽고 상태를 따로 연동할 필요가 없음 이러게되면 상태관리가 쉬움
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = pyeong,
            onValueChange = onStateChange,
            label = {
                Text("평")
            }
        )
        OutlinedTextField(
            value = squaremeter,
            onValueChange = {},
            label = {
                Text("제곱미터")
            }
        )
    }
}