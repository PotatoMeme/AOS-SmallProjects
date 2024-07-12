package com.potatomeme.sample_compose.component_example

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewDropDownMenu() {
    SampleComposeTheme {
        DropDownMenuExam()
    }
}

@Composable
fun SampleDropDownMenu() {
    var expandDropDownMenu by remember { mutableStateOf(false) }
    DropdownMenu(expanded = expandDropDownMenu, onDismissRequest = { expandDropDownMenu = false }) {
        DropdownMenuItem(text = { Text("test") }, onClick = { /*TODO*/ })
    }
}

@Composable
fun DropDownMenuExam() {
    var expandDropDownMenu by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableIntStateOf(0) }
    val arr = arrayListOf<Pair<Int, String>>().apply {
        repeat(4) {
            add(
                Pair(it, "test str $it")
            )
        }
    }

    Column(modifier = Modifier.fillMaxWidth().background(Color.Blue)) {
        Button(
            onClick = { expandDropDownMenu = true }
        ) {
            Text("드롭다운 메뉴 열기")
        }
        Text(modifier = Modifier.fillMaxWidth().background(Color.Cyan), text = "현재 선택된 index: $selectedItem , value : ${arr[selectedItem].second}")
        DropdownMenu(
            expanded = expandDropDownMenu,
            onDismissRequest = { expandDropDownMenu = false },
            modifier = Modifier
                //.padding(10.dp)
                .clip(RoundedCornerShape(10))
                .fillMaxWidth(0.8f)
                .background(Color.Green)
                .border(BorderStroke(5.dp, Color.Gray), RoundedCornerShape(10)),
            //offset = DpOffset(0.dp, 10.dp)
        ) {
            arr.forEach {
                DropdownMenuItem(
                    text = { Text(it.second) },
                    onClick = {
                        selectedItem = it.first
                        expandDropDownMenu = false
                    })
            }
        }
    }
}