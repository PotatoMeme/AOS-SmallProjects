package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
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
fun ImageExample() {
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

@Composable
fun ImageToNetWorkWithCoil(imageStr: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //스크롤 위치를 변경하거나 현재 상태를 가져올 수 ScrollState
            .verticalScroll(rememberScrollState())
    ) {

        // AsyncImage 로 가져오기 : Coil 권장
        AsyncImage(
            model = imageStr,
            modifier = Modifier.clip(CircleShape),
            contentDescription = "Image to basic AsyncImage",
        )

        // AsyncImage 로 비동기 이미지 가져오기 : Coil 권장
        val imageRequest: ImageRequest = ImageRequest.Builder(LocalContext.current)
            .data(imageStr)
            .crossfade(true)
            .build()

        AsyncImage(
            model = imageRequest,
            //placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "Image to ImageRequest",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape)
        )

        //하위 구성 비동기 이미지
        SubcomposeAsyncImage(
            model = imageStr,
            modifier = Modifier.clip(CircleShape),
            loading = { loading: AsyncImagePainter.State.Loading ->
                CircularProgressIndicator()
            },
            contentDescription = "Image to SubcomposeAsyncImage with loading"
        )

        SubcomposeAsyncImage(
            model = imageStr,
            modifier = Modifier.clip(CircleShape),
            contentDescription = "Image to SubcomposeAsyncImage with Content"
        ) {
            this//SubcomposeAsyncImageScope
            val state = this.painter.state
            when (state) {
                is AsyncImagePainter.State.Error, is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        //deprecated
        //val painter = rememberImagePainter(data = imageStr)

        val painter : AsyncImagePainter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageStr)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        )

        if (painter.state is AsyncImagePainter.State.Success) {
            // This will be executed during the first composition if the image is in the memory cache.

        }

        Image(
            painter = painter,
            contentDescription = "Image to Painter"
        )

        Text("END", modifier = Modifier.padding(2.dp))
    }
}