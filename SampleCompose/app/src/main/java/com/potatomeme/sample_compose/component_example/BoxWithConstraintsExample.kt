package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun BoxWithConstraintsPreview() {
    SampleComposeTheme {
        Column(
            modifier = Modifier.height(450.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BoxWithConstraintsExample(300.dp)
            BoxWithConstraintsExample(100.dp)
        }
    }
}

@Composable
fun SimpleBoxWithConstraints() {
    BoxWithConstraints() {

    }
}

@Composable
fun BoxWithConstraintsExample(size: Dp) {
    BoxWithConstraints(
        modifier = Modifier
            .size(size)
            .background(Color.Gray),
        contentAlignment = Alignment.Center,
        //Whether the incoming min constraints should be passed to content.
        //들어오는 최소 제약 조건을 콘텐츠에 전달해야 하는지 여부입니다.
        propagateMinConstraints = false
    ) {


        // BoxWithConstraints는 Box와의 차별점은 이것
        // Layout의 Constraint에 접근할수 있다는것

        // BoxWithConstraints를 만들때 BoxWithConstraintsScope의 구현체를 생성합니다.
        //interface BoxWithConstraintsScope : BoxScope {
        //    val constraints: Constraints
        //    val minWidth: Dp
        //    val maxWidth: Dp
        //    val minHeight: Dp
        //    val maxHeight: Dp
        //}
        // content 는 이BoxWithConstraintsScope 에서 이루어 집니다
        this//BoxWithConstraintsScope
        // 그래서 content 에서 BoxWithConstraintsScope를 이용하여 상황에 맞는 Layout을 표현할수있습니다.
        if (maxHeight > 150.dp) {
            Text(
                text = "완전 기네요!",
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
        Text("maxW:$maxWidth maxH:$maxHeight minW: $minWidth minH:$minHeight")
    }
}