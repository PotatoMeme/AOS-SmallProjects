package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme


@Preview(showBackground = true)
@Composable
fun surfacePreview() {
    SampleComposeTheme {
        SurfaceExample()
    }
}

@Composable
fun SimpleSurface() {
    Surface() { }
}


// Surface 는 4종류의 생성자가 있음
// Surface 자체적으로 클릭이벤트를 받는 3개의 생성자 이벤트를 따로 받지않는 생성자가 있습니다.
// Surface 받는 입력은 onClick이벤트를 받는 생성자가 2개 토글을 지원하는 생성자 1개로 나뉩니다
// onClick이벤트를 받는 생성자의 경우 차이점은 selected입니다.
// selected 를 이용하면 선택 상태를 나타내고, 이를 통해 시각적, 기능적, 접근성 측면에서 다양한 처리를 가능하다고 합니다.
@Composable
fun SurfaceExample() {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isPressed by interactionSource.collectIsPressedAsState()
    val isDragged by interactionSource.collectIsDraggedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    Surface(
        onClick = {

        },
        modifier = Modifier
            .size(300.dp)
            .background(Color.White)
            .padding(30.dp),
        enabled = true,
        shape = CircleShape,
        color = Color.White,
        contentColor = Color.White,
        // 색상이 ColorScheme.surface일 때, 높은 elevation은 밝은 테마에서 더 어두운 색을,
        // 어두운 테마에서 더 밝은 색을 결과적으로 나타냅니다.
        tonalElevation = 30.dp,
        shadowElevation = 10.dp,
        border = BorderStroke(10.dp, Color.Black),
        interactionSource = interactionSource
    ) {
        Box(
            modifier = Modifier
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .background(Color.Red),
                text = "Test Text",
                fontSize = 30.sp,
            )
        }
    }
}