package com.potatomeme.sample_compose.component_example


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewSnackBar() {
    SampleComposeTheme {
        SnackBarExam1()
    }
}


@Composable
fun SnackBarExam1() {
    var openSnackBar by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { openSnackBar = !openSnackBar }) {
            Text("스낵바 열기")
        }
    }

    if (openSnackBar) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Snackbar(
                //modifier: Modifier = Modifier,
                modifier = Modifier.padding(5.dp),
                //action: @Composable (() -> Unit)? = null,
                action = {
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .clickable {
                                openSnackBar = false
                            }, text = "confirm"
                    )
                },
                //dismissAction: @Composable (() -> Unit)? = null,
                dismissAction = {
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .clickable {
                                openSnackBar = false
                            }, text = "dismiss"
                    )
                },
                //actionOnNewLine: Boolean = false,
                //actionOnNewLine= true,
                //shape: Shape = SnackbarDefaults.shape,
                //containerColor: Color = SnackbarDefaults.color,
                //contentColor: Color = SnackbarDefaults.contentColor,
                //actionContentColor: Color = SnackbarDefaults.actionContentColor,
                //dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
            ) {
                Text(text = "Test")
            }
        }
    }
}
