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
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //@CustomQualifier
    //@Named("test")
    @Inject
    lateinit var myName: MyName

    @Inject
    lateinit var app: Application

    @Inject
    lateinit var testClassB1: TestClassB

    private lateinit var testClassB2: TestClassB

    @Inject
    lateinit var lazyTestClass : Lazy<TestClassA>
    @Inject
    lateinit var lazyTestClassToCompare : Lazy<TestClassA>

    @Inject
    lateinit var providerTestClass1 : Provider<TestClassB>

    @Inject
    lateinit var providerTestClass2 : Provider<TestClassA>

    @Inject
    fun injectTestClassB2(testClassB: TestClassB) {
        testClassB2 = testClassB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "onCreate: app = $app")
        assert(this::testClassB1.isInitialized)
        testClassB1.printUUID()
        assert(this::testClassB2.isInitialized)
        testClassB2.printUUID()


        assert(this::lazyTestClass.isInitialized)
        assert(lazyTestClass.get() === lazyTestClassToCompare.get())
        Log.e("TAG", "onCreate: lazyTestClass time1, ${lazyTestClass.get().getUUID()}")
        Log.e("TAG", "onCreate: lazyTestClass time2, ${lazyTestClass.get().getUUID()}")
        assert(this::providerTestClass1.isInitialized)
        assert(providerTestClass1.get() !== providerTestClass1.get())
        Log.e("TAG", "onCreate: providerTestClass1 time1, ${providerTestClass1.get().getUUID()}")
        Log.e("TAG", "onCreate: providerTestClass1 time2, ${providerTestClass1.get().getUUID()}")

        assert(this::providerTestClass2.isInitialized)
        assert(providerTestClass2.get() === providerTestClass2.get())
        Log.e("TAG", "onCreate: providerTestClass2 time1, ${providerTestClass2.get().getUUID()}")
        Log.e("TAG", "onCreate: providerTestClass2 time2, ${providerTestClass2.get().getUUID()}")


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