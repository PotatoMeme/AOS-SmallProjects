package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Preview(showBackground = true)
@Composable
fun PreviewConstraintLayout() {
    SampleConstraintLayoutType1()
}

@Composable
fun SimpleConstraintLayout() {
    ConstraintLayout() {

    }
}

@Composable
fun SampleConstraintLayoutType1() {
    ConstraintLayout(
        //modifier: androidx.compose.ui.Modifier = COMPILED_CODE,
        modifier = Modifier.fillMaxSize(),
        //optimizationLevel: kotlin.Int = COMPILED_CODE
        //optimizationLevel = 0,
    ) {
        // createRefs는 여러개의 레퍼런스를 리턴하니 destruction으로 분해합시다.
        // xml 의 id라 생각하면됨
        val (redBox, magentaBox, greenBox, yellowBox) = createRefs()
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red)
                .constrainAs(redBox) {
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end, 30.dp)
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Magenta)
                //
                .constrainAs(magentaBox) {
                    start.linkTo(parent.start, 10.dp)
                    end.linkTo(parent.end, 30.dp)
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Green)
                .constrainAs(greenBox) {
                    centerTo(parent)
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Yellow)
                .constrainAs(yellowBox) {
                    start.linkTo(greenBox.end)
                    top.linkTo(greenBox.bottom)

                }
        )
    }
}

@Composable
fun SampleConstraintLayoutType2() {
    ConstraintLayout(
        // constraintSet: androidx.constraintlayout.compose.ConstraintSet,
        // constraintSet = ConstraintSet(""),
        // modifier: androidx.compose.ui.Modifier = COMPILED_CODE,
        modifier = Modifier,
        // optimizationLevel: kotlin.Int = COMPILED_CODE,
        // optimizationLevel = 0,
        // animateChanges: kotlin.Boolean = COMPILED_CODE,
        // animateChanges = false,
        // animationSpec: androidx.compose.animation.core.AnimationSpec<kotlin.Float> = COMPILED_CODE,
        // noinline finishedAnimationListener: (() -> kotlin.Unit)? = COMPILED_CODE
    ) {

    }
}