package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

fun getModifierExample(): Modifier = Modifier
    /*.background(
        brush = Brush.verticalGradient(listOf(Color.Red, Color.Blue)),
        // brush = Brush.verticalGradient(colors = listOf(Color.Red, Color.Blue),startY = 100f,endY = 200f),
        // brush = Brush.horizontalGradient(colors = listOf(Color.Red, Color.Blue)),
        // brush = Brush.linearGradient(colors = listOf(Color.Red, Color.Blue), start = Offset.Zero, end = Offset.Infinite),
        // brush = Brush.radialGradient(colors = listOf(Color.Red, Color.Blue)),
        // brush = Brush.sweepGradient(colors = listOf(Color.Red, Color.Blue)),
        shape = CircleShape,
        alpha = 0.4f
    )*/
    .background(
        color = Color.Gray.copy(alpha = 0.5f),
        //shape = CircleShape
        //shape = RectangleShape
        //shape = CutCornerShape(30.dp)
        //shape = RoundedCornerShape(30.dp)
        shape = RoundedCornerShape(
            topStartPercent = 20,
            topEndPercent = 20,
            bottomStartPercent = 20,
            bottomEndPercent = 20,
        )
    )
    //.width(30.dp)
    //.height(30.dp)
    //.fillMaxWidth(0.5f)
    //.fillMaxHeight(0.5f)
    //.fillMaxSize()
    //컨텐츠의 선호 크기를 정확히 `size` dp의 사각형으로 선언합니다.
    .size(300.dp)
    //자식 컴포넌트의 크기 측정값에 영향을 주지 않고 자식 컴포넌트를 x축 혹은 y축으로 얼만큼 이동할 것인지 결정하는 값
    .offset(x = 10.dp, y = 10.dp)
    .border(
        width = 20.dp,
        color = Color.Red,
        shape = RectangleShape
    )
    .padding(20.dp)
    .border(
        width = 20.dp,
        color = Color.Green,
        shape = RectangleShape
    )
    .padding(20.dp)
    .border(
        width = 20.dp,
        color = Color.Blue,
        shape = RectangleShape
    )
    .padding(20.dp)