package com.potatomeme.compose_ui_sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.potatomeme.compose_ui_sample.ui.theme.ComposeUISampleTheme

class MainActivity : ComponentActivity() {
    companion object{
        private const val TAG = "MainActivity"
    }


    private val uiDescriptionList: List<UIDescription> = listOf(
        UIDescription(
            "SampleTitle",
            "this is sample description"
        ) {
            Log.d(TAG, "UIDescription action : SampleTitle")
            startActivityWithCls(com.potatomeme.sample.MainActivity::class.java)
        }
    )

    private fun <T> startActivityWithCls(cls: Class<T>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUISampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UIDescriptionList(list = uiDescriptionList)
                }
            }
        }
    }
}

data class UIDescription(
    val title: String, // 이름
    val description: String, // 설명
    val action: () -> Unit = {}, // 해당 아이템을 클릭할때 실행되는 동작
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIDescriptionList(list: List<UIDescription>) {
    LazyColumn {
        items(list) { uiDescription ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onClick = { uiDescription.action() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = uiDescription.title,
                        fontSize = 25.sp,
                        maxLines = 1,
                    )
                    Text(
                        text = uiDescription.description,
                        fontSize = 15.sp,
                        minLines = 2,
                        maxLines = 2,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UIDescriptionListPreview() {
    ComposeUISampleTheme {
        UIDescriptionList(
            list = listOf(
                UIDescription(
                    "SampleTitle",
                    "this is sample description"
                )
            )
        )
    }
}