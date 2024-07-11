package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewConstraintLayout() {
    SampleComposeTheme {
        ConstraintLayoutExam4()
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
    val constraintSet = ConstraintSet {
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
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(boxBarrier)
                }
                .width(100.dp)
        )
    }
}

@Composable
fun ConstraintLayoutExam4() {
    val scrollState = rememberScrollState()
    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            repeat(30) {
                ConstraintLayoutExam4_1()
            }
        }
    }
}

@Composable
fun ConstraintLayoutExam4_1() {
    Card(modifier = Modifier.padding(4.dp)) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (profileImage, author, description) = createRefs()
            AsyncImage(
                model = "https://avatars.githubusercontent.com/u/89847975?v=4",
                contentDescription = "image description",
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(color = Color.Green),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .constrainAs(profileImage) {
                        start.linkTo(parent.start, margin = 8.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.value(40.dp)
                    }
            )

            Text(
                text = "작가",
                modifier = Modifier.constrainAs(author) {
                    linkTo(profileImage.end, parent.end, 8.dp, 8.dp, bias = 0f)
                }
            )
            Text(
                text = "설명",
                modifier = Modifier.constrainAs(description) {
                    linkTo(profileImage.end, parent.end, 8.dp, 8.dp, bias = 0f)
                    width = Dimension.fillToConstraints
                }
            )
            val chain = createVerticalChain(author, description)
            constrain(chain) {
                top.linkTo(parent.top, margin = 8.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
            }
        }
    }
}