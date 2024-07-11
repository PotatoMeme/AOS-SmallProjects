package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewConstraintLayout() {
   SampleComposeTheme {
       ConstraintLayoutExam3()
   }
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

@Composable
fun ConstraintLayoutExam1() {
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
fun ConstraintLayoutExam2() {
    val constraintSet = ConstraintSet{
        //레퍼런스 생성
        val redBox = createRefFor("redBox")
        val magentaBox = createRefFor("magentaBox")
        val greenBox = createRefFor("greenBox")
        val yellowBox = createRefFor("yellowBox")

        //레퍼런스 constrain 지정
        constrain(redBox) {
            bottom.linkTo(parent.bottom, 10.dp)
            end.linkTo(parent.end, 30.dp)
        }

        constrain(magentaBox) {
            start.linkTo(parent.start, 10.dp)
            end.linkTo(parent.end, 30.dp)
        }

        constrain(greenBox) {
            centerTo(parent)
        }

        constrain(yellowBox) {
            start.linkTo(greenBox.end)
            top.linkTo(greenBox.bottom)
        }
    }

    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red)
                .layoutId("redBox")
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Magenta)
                .layoutId("magentaBox")
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Green)
                .layoutId("greenBox")
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Yellow)
                .layoutId("yellowBox")
        )
    }
}

@Composable
fun ConstraintLayoutExam3() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (redBox, yellowBox, magentaBox, text) = createRefs()
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red)
                .constrainAs(redBox) {
                    start.linkTo(parent.start)
                }
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Yellow)
                .constrainAs(yellowBox) {
                    start.linkTo(parent.start, margin = 40.dp)
                }
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Magenta)
                .constrainAs(magentaBox) {
                    start.linkTo(parent.start, margin = 80.dp)
                }
        )

        /*createHorizontalChain(
            redBox, yellowBox, magentaBox,
            chainStyle = ChainStyle.SpreadInside
        )*/
        createVerticalChain(
            redBox, yellowBox, magentaBox,
            chainStyle = ChainStyle.SpreadInside
        )

        val boxBarrier = createEndBarrier(redBox, yellowBox, magentaBox)
        Text(
            text = "나랏말싸미 듕귁에 달아 문자와로 서로 사맛디 아니할쌔 어린 백성이 니르고저 핣배이셔도",
            modifier = Modifier.constrainAs(text) {
                start.linkTo(boxBarrier)
            }.width(100.dp)
        )
    }
}