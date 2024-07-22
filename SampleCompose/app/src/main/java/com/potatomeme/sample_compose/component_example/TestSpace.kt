package com.potatomeme.sample_compose.component_example

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme

@Preview(showBackground = true)
@Composable
fun PreviewTest() {
    SampleComposeTheme {
        TestSpace1(false)
    }
}

// @Composable의 생명주기
// composable 함수도 마찬가지로 생명주기를 가지고 있음
// Initial Composition - Composable이 처음 생성될 때
// Recomposition - UI를 구성하는 데이터가 변경되었을 때 (주로 State<T>가 바뀌거나 Composable 함수의 매개변수의 값이 변화할 때 실행된다)
// Decomposition - Composable이 파괴될 때


/**
 * remember 와 MutableState
 * */
@Composable
fun TestSpace1(testA2: Boolean) {
    var testA1 = false

    // remember
    // Composable함수
    // 재구성이 되었을 때도 값을 저장할 수 있도록 하기 위하여 Compose에서는 remember 키워드를 제공
    // remember composable 함수는 initial composition 때 저장한 값을 앞으로의 recomposition에서 사용할 수 있도록 해준다.
    // 즉 recomposition을 할 때는 값을 새롭게 생성하지 않고 기존에 저장되어 있던 데이터를 반환한다는 의미
    // remember 함수는  Composable이 어디에 위치했느냐에 따라서 다른 결과를 반환한다.
    // 이는 Positional Memoization이라는 기법을 사용했기 때문에 가능한 결과
    // recomposition이 매우 자주 실행될 수 있기 때문에 생성하는 데 오래 걸릴 만한 것들은 remember을 사용해서,
    // 값을 recomposition을 할 때마다 새로 생성하지 않고 원래 있던 것을 쓰고 싶을 때 쓰면 된다.
    // remember(key: Any?, calculation: () -> T) 함수도 준비되어 있다.
    // 이 함수는 key에 들어온 값이 변경되었을 때 calculation을 다시 수행한다

    // MutableStat
    // MutableState 클래스는 Compose에 의해 읽기와 쓰기를 관찰하는 단일 값 보유자
    // Jetpack Compose에서는 State<T>의 값이 변화하면 recomposition이 일어난다.


    val testA3 = remember {
        mutableStateOf(false)
    }

    val testA4 = remember {
        mutableStateOf(testA2)
    }

    val testA5 = remember(key1 = testA2) {
        mutableStateOf(testA2)
    }


    Log.d("******", "TestSpace: $testA1 ${testA3.value}")

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = testA1, onCheckedChange = { testA1 = !testA1 })
            //이경우 Recomposition이 돠지 않음
            Text(text = "testA1 state $testA1")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = testA2, onCheckedChange = { })
            Text(text = "testA2 state $testA2")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = testA3.component1(), onCheckedChange = testA3.component2())
            Text(text = "testA3 state ${testA3.value}")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = testA4.component1(), onCheckedChange = { })
            Text(text = "testA4 state $testA4")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = testA5.component1(), onCheckedChange = {})
            Text(text = "testA5 state $testA5")
        }
    }
}

@Composable
fun TestSpace2() {
val names = remember {
    mutableStateListOf<String>()
}

Log.d("********", "TestSpace2: ${names.size}")
LaunchedEffect(true) {
    names.add("test")
}
}