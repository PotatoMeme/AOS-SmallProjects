package com.potatomeme.sample_compose.component_example

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme


@Preview(showBackground = true)
@Composable
fun PreviewAnimation() {
    SampleComposeTheme {
        CrossFadeExample1()
    }
}

@Composable
fun AnimatedVisibilityExample1() {
    var helloWorldVisible by remember { mutableStateOf(true) }
    var isRed by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isRed) Color.Red else Color.White, label = "ColorAnimation"
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(backgroundColor)
    ) {

        AnimatedVisibility(
            visible = helloWorldVisible,
            enter = scaleIn() + slideInHorizontally(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Text(text = "Hello World!")
        }
        Row(
            Modifier.selectable(
                selected = helloWorldVisible,
                onClick = {
                    helloWorldVisible = true
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = helloWorldVisible,
                onClick = { helloWorldVisible = true }
            )
            Text(
                text = "Hello World 보이기"
            )
        }

        Row(
            Modifier.selectable(
                selected = !helloWorldVisible,
                onClick = {
                    helloWorldVisible = false
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !helloWorldVisible,
                onClick = { helloWorldVisible = false }
            )
            Text(
                text = "Hello World 감추기"
            )
        }

        Text(text = "배경 색을 바꾸어봅시다.")

        Row(
            Modifier.selectable(
                selected = !isRed,
                onClick = {
                    isRed = false
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !isRed,
                onClick = { isRed = false }
            )
            Text(
                text = "흰색"
            )
        }

        Row(
            Modifier.selectable(
                selected = isRed,
                onClick = {
                    isRed = true
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isRed,
                onClick = { isRed = true }
            )
            Text(
                text = "빨간색"
            )
        }
    }
}

@Composable
fun CrossFadeExample1() {
    var isDarkMode by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isDarkMode, label = "다크모드")

    val backgroundColor by transition.animateColor(label = "다크모드 배경색상") { state ->
        when (state) {
            false -> Color.White
            true -> Color.Black
        }
    }

    val color by transition.animateColor(label = "글자 색상") { state ->
        when (state) {
            false -> Color.Black
            true -> Color.White
        }
    }

    val alpha by transition.animateFloat(label = "알파") { state ->
        when (state) {
            false -> 0.7f
            true -> 1.0f
        }
    }

    // 단계 5: 컬럼에 배경과 알파를 적용합시다.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .alpha(alpha)
    ) {
        // 단계 6: 라디오 버튼에 글자 색을 적용합시다.
        RadioButtonWithText(text = "일반 모드", color = color, selected = !isDarkMode) {
            isDarkMode = false
        }
        RadioButtonWithText(text = "다크 모드", color = color, selected = isDarkMode) {
            isDarkMode = true
        }

        // 단계 7: Crossfade를 이용해 `isDarkMode`가 참일 경우
        // `Row`로 항목을 표현하고 거짓일 경우 `Column`으로 표현해봅시다.
        Crossfade(targetState = isDarkMode, label = "CrossFade") {
            when (it) {
                true -> {
                    Row {
                        Box(modifier = Modifier
                            .background(Color.Red)
                            .size(20.dp)) {
                            Text("1")
                        }
                        Box(modifier = Modifier
                            .background(Color.Magenta)
                            .size(20.dp)) {
                            Text("2")
                        }
                        Box(modifier = Modifier
                            .background(Color.Blue)
                            .size(20.dp)) {
                            Text("3")
                        }
                    }
                }
                false -> {
                    Column {
                        Box(modifier = Modifier
                            .background(Color.Red)
                            .size(20.dp)) {
                            Text("A")
                        }
                        Box(modifier = Modifier
                            .background(Color.Magenta)
                            .size(20.dp)) {
                            Text("B")
                        }
                        Box(modifier = Modifier
                            .background(Color.Blue)
                            .size(20.dp)) {
                            Text("C")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RadioButtonWithText(
    text: String,
    color: Color = Color.Black,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.selectable(
            selected = selected,
            onClick = onClick
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = text, color = color)
    }
}