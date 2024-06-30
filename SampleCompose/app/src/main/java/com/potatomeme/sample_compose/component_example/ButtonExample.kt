package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    ButtonExample {

    }
}

@Composable
fun SimpleButton() {
    Button(
        onClick = {},
        content = {}
    )
}

@Composable
fun ButtonExample(onButtonClicked: () -> Unit) {

    // 상태 감지
    val interactionSource = remember {
        MutableInteractionSource()
    }

    // 버튼이 눌러진 상태를 isPressed에 저장
    // interactionSource 변수를 원하는 버튼에 지정하면 그 버튼의 상태를 isPressed에 저장하는 것임
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressState = if (isPressed) {
        "버튼 누르고 있는 중"
    } else {
        "버튼 누르는 것 stop"
    }

    Button(
        onClick = onButtonClicked,
        /*modifier = Modifier
            .width(200.dp)
            .height(200.dp),*/
        // controls the enabled state of this button.
        // When false, this component will not respond to user input,
        // and it will appear visually disabled and disabled to accessibility services.
        // 이 버튼의 활성화 상태를 제어합니다.
        // false일 경우, 이 구성 요소는 사용자 입력에 응답하지 않으며,
        // 시각적으로 비활성화된 상태로 표시되고 접근성 서비스에서도 비활성화된 것으로 나타납니다.
        enabled = true,
        shape = CircleShape,
        // ButtonColors that will be used to resolve the colors for this button in different states.
        // See ButtonDefaults.buttonColors.
        // 이 버튼의 다양한 상태에서 색상을 결정하는 데 사용되는 ButtonColors.
        // ButtonDefaults.buttonColors를 참조하세요.
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Green,
            contentColor = Color.Black,

            // when enabled is false
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
        // ButtonElevation used to resolve the elevation for this button in different states.
        // This controls the size of the shadow below the button.
        // Additionally, when the container color is ColorScheme.surface,
        // this controls the amount of primary color applied as an overlay.
        // See ButtonElevation.shadowElevation and ButtonElevation
        // 이 버튼의 다양한 상태에서 높이를 결정하는 데 사용되는 ButtonElevation.
        // 이는 버튼 아래의 그림자 크기를 제어합니다.
        // 또한, 컨테이너 색상이 ColorScheme.surface일 경우,
        // 주 색상이 오버레이로 적용되는 양을 제어합니다.
        // ButtonElevation.shadowElevation 및 ButtonElevation을 참조하세요
        elevation = ButtonDefaults.buttonElevation(),
        //이 버튼의 컨테이너 주위에 그릴 테두리
        border = BorderStroke(2.dp, Color.Blue),
        contentPadding = PaddingValues(20.dp),
        // the MutableInteractionSource representing the stream of Interactions for this button.
        // You can create and pass in your own remembered instance to observe
        // Interactions and customize the appearance / behavior of this button in different states.
        // 이 버튼에 대한 상호작용 스트림을 나타내는 MutableInteractionSource입니다.
        // 상호작용을 관찰하고 이 버튼의 다양한 상태에서 외관/동작을 사용자 지정하려면
        // 직접 기억된 인스턴스를 생성하여 전달할 수 있습니다.
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = Icons.Filled.Send,
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.size(ButtonDefaults.IconSpacing)
        )
        Text(text = pressState)
    }
}
