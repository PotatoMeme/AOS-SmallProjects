package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun CanvasPreview() {
    SampleComposeTheme {
        CanvasExam()
    }
}

@Composable
fun SimpleCanvasType1() {
    Canvas(modifier = Modifier) { }
}

@Composable
fun SimpleCanvasType2() {
    Canvas(modifier = Modifier, contentDescription = "sample description") { }
}

@Composable
fun CanvasExam(){
    Canvas(modifier = Modifier.size(100.dp)) {
        this.size
        this.center
        this.layoutDirection
        this.drawContext

        this.drawLine(Color.Red, Offset(0f,0f), Offset(200f,200f),40f)
        this.drawCircle(Color.Green,30f,this.center)
        this.drawRect(Color.Blue,this.center, Size(30f,30f))
        this.drawArc(Color.Black,30f,30f,true,)


    }
}