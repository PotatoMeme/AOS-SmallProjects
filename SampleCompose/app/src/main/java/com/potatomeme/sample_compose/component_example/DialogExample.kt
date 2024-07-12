package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewDialog() {
    SampleComposeTheme {
        DialogExamType1()
    }
}

@Composable
fun SampleDialogType1() {
    Dialog(onDismissRequest = { /*TODO*/ }) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleDialogType2() {
    AlertDialog(onDismissRequest = { /*TODO*/ }) {

    }
}
@Composable
fun DialogExamType1() {
    var openDialog by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { openDialog = !openDialog }) {
            Text("다이얼로그 열기")
        }
    }

    if (openDialog) {
        Dialog(onDismissRequest = {
            openDialog = false
        }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text("다이얼로그 열기")
            }
        }
    }
}

@Composable
fun DialogExamType2() {
    var openDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }

    Column {
        Button(onClick = { openDialog = !openDialog }) {
            Text("다이얼로그 열기")
        }
        Text("카운터: $counter")
    }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog = false
            }, confirmButton = {
                Button(
                    onClick = {
                        counter++
                        openDialog = false
                    }
                ) {
                    Text("더하기")
                }
            }, dismissButton = {
                Button(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("취소")
                }
            }, title = {
                Text("더하기")
            }, text = {
                Text("1을 더하는 간단한 다이얼로그입니다.\n1을 더하겠습니까?")
            })
    }
}