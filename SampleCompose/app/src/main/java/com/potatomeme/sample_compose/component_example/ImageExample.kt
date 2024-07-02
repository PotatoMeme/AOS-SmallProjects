package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.R
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun ImagePreview() {
    SampleComposeTheme {
        ImageExample()
    }
}

@Composable
fun SimpleImage() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "image-painter"
        )
        Image(
            imageVector = Icons.Filled.Face,
            contentDescription = "image-imageVector"
        )
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.ic_launcher_foreground),
            contentDescription = "image-bitmap"
        )
    }
}

@Composable
fun ImageExample(){
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "image-painter",

            modifier = Modifier
                .background(Color.Gray)
                .padding(),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.None,
            //alpha = 0.5f,
            colorFilter = ColorFilter.tint(Color.White)
        )
        Image(
            imageVector = Icons.Filled.Face,
            contentDescription = "image-imageVector",

            modifier = Modifier
                .background(Color.Black)
                .padding(),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.None,
            //alpha = 0.5f,
            colorFilter = ColorFilter.tint(Color.White)
        )
       /* Image(
            bitmap = ImageBitmap.imageResource(R.drawable.ic_launcher_foreground),
            contentDescription = "image-bitmap"
        )*/
    }
}