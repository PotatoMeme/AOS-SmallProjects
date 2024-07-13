package com.potatomeme.sample_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.potatomeme.sample_compose.component_example.AnimatedVisibilityExample
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val flow = flow<Boolean> {
        repeat(10){
            Log.d(TAG, "flow repeat now : $it")
            emit(false)
            delay(1000L)
            emit(true)
            delay(1000L)
        }
    }.stateIn(
        scope = lifecycleScope,
        started = SharingStarted.Lazily,
        initialValue = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

            flow.collect{
                setContent {
                    SampleComposeTheme {
                        // A surface container using the 'background' color from the theme
                        /*Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            //Greeting("Android")
                            *//*ButtonExample {
                        Log.d(TAG, "button is Clicked")
                    }*//*
                    *//*val imageStr = "https://avatars.githubusercontent.com/u/89847975?v=4"
                    ImageToNetWorkWithCoil(imageStr = imageStr)*//*
                    *//*CheckBoxExample()*//*

                }*/
                        /*Column {
                            TextFieldExample()
                            TextFieldExample()
                        }*/
                        //TestSpace1(testA2 = it)
                        //ConstraintLayoutExam4()
                        //DialogExamType2()
                        /*Box {
                            Column {
                                Demo_SearchableExposedDropdownMenuBox()
                            }
                        }*/
                        //SnackBarExam1()
                        //SnackBarExam2()
                        AnimatedVisibilityExample()
                    }
                }
            }
        }
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SampleComposeTheme {
        Greeting("Android")
    }
}