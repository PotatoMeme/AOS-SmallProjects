package com.potatomeme.hilt_sample

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.potatomeme.hilt_sample.ui.theme.SampleComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var myName: MyName

    @Inject
    lateinit var app: Application

    @Inject
    lateinit var testClassB1: TestClassB

    private lateinit var testClassB2: TestClassB

    @Inject
    fun injectTestClassB2(testClassB: TestClassB){
        testClassB2 = testClassB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "onCreate: app = $app", )
        assert(this::testClassB1.isInitialized)
        testClassB1.printUUID()
        assert(this::testClassB2.isInitialized)
        testClassB2.printUUID()
        setContent {
            SampleComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(myName.toString())
                }
            }
        }
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