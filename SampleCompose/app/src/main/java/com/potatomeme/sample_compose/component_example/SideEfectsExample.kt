package com.potatomeme.sample_compose.component_example

import android.media.Image
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.potatomeme.sample_compose.ui.theme.SampleComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewSideEffects() {
    SampleComposeTheme {
        LaunchEffectExample(false)
    }
}
// 부수 효과
// Compose 구성요소들은 언제 리컴포지션이 발생할 지 모르기 때문에
// Compose 함수에서 부수 효과를 발생시키는 경우 예상치 못한 현상이 발생할 수도 있음
// 부수 효과가 필요할 때도 있기 때문에 이를 위해서 나온 게 바로 부수 효과(Side Effect) API


// LaunchedEffect는 state 형태의 key 값과 실행 블럭을 매개변수로 받는다.
// key 값에 변화가 생기면 실행 블럭 내부에 있는 코드가 실행
@Composable
fun LaunchEffectExample(
    state: Boolean,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    if (!state) {
        // `LaunchedEffect` will cancel and re-launch if
        // `scaffoldState.snackbarHostState` changes
        LaunchedEffect(snackBarHostState) { // key가 바뀌면 다시 코루틴을 만들어 수행
            // Show snackbar using a coroutine, when the coroutine is cancelled the
            // snackbar will automatically dismiss. This coroutine will cancel whenever
            // `state` is true, and only start when `state` is false
            // (due to the above if-check), or if `scaffoldState.snackbarHostState` changes.
            snackBarHostState.showSnackbar(
                message = "Error, State is false",
                actionLabel = "Retry Message",
            )//showSnackbar는 중단함수
            //@Composable invocations can only happen from the context of a @Composable function
            //@Composable 호출은 @Composable 함수의 컨텍스트에서만 발생할 수 있습니다.
            /*Button(onClick = { *//*TODO*//* }) {

            }*/
        }

        // 일반적으로 호출할경우 컴파일 에러로 사용하지 못함
        /*snackBarHostState.showSnackbar(
            message = "Error, State is false",
            actionLabel = "Retry Message",
        )*/
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }, content = { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("test")
        }
    })
}

@Composable
fun RememberCoroutineScopeExample() {
    // rememberCoroutineScope는 호출된 컴포지션에 바인딩되어있는 scope를 반환하기 때문에 해당 컴포지션이 취소되면 coroutineScope도 같이 취소된다.
    // rememberCoroutineScope 의 코루틴 스코프는 컴포저블 함수의 생명주기만을 따르는 거지 실제 코루틴의 위치는 컴포저블 함수 외부에 있다
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }, content = { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = "ButtonClicked",
                        actionLabel = "Retry Message",
                    )
                }
                /*LaunchedEffect(key1 = snackBarHostState) {
                    snackBarHostState.showSnackbar(
                        message = "ButtonClicked",
                        actionLabel = "Retry Message",
                    )
                }*///@Composable 호출은 @Composable 함수의 컨텍스트에서만 발생할 수 있습니다.

            }) {

            }
        }
    })
}

@Composable
fun RememberUpdateStateExample(onTimeout: () -> Unit) {
    /*val (sample1,sample2) = remember(onTimeout) {
        mutableStateOf(onTimeout)
    }*/
    val currentOnTimeout by rememberUpdatedState(newValue = onTimeout)
    //fun <T> rememberUpdatedState(newValue: T): State<T> = remember {
    //    mutableStateOf(newValue)
    //}.apply { value = newValue }

    LaunchedEffect(key1 = true) {
        // key가 true 로 절대 별하지않음
        // 그래서 onTimeOut이 바뀌어서 ReComposition이 된다 하더라도
        // key가 바뀌지않아 다시 호출되지 않고 스킵이됩니다
        delay(10000L)
        //onTimeout()
        //10000L이 지나지 않았을때 바뀌었다면 바뀌기 전의 함수가아닌 바뀐 함수가 호출됨
        currentOnTimeout()
    }
}

//정리가 필요한 효과
@Composable
fun DisposableEffectExample(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit,
    onStop: () -> Unit,
) {
    val currentOnStart by rememberUpdatedState(newValue = onStop)
    val currentOnStop by rememberUpdatedState(newValue = onStart)

    DisposableEffect(key1 = lifecycleOwner) { // key 바뀌지 않을 경우 유지
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose { // 이단계에서 리소스등을 관리할때도 사용
            //컴포지션에서 이펙트가 나가도 onDispose 호출
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

//Compose 상태를 비 Compose코드에 게시
//@Composable
/*fun SideEffectExample(user: User): FirebaseAnalytics {
    val analytics: FirebaseAnalytics = remember {
        FirebaseAnalytics()
    }

    // On every successful composition, update FirebaseAnalytics with
    // the userType from the current User, ensuring that future analytics
    // events have this metadata attached
    SideEffect {
        analytics.setUserProperty("userType", user.userType)
    }
    return analytics
}*/

// 비 Compose 상태를 Compose 상태로 변환
/*@Composable
fun ProduceStateExample(
    url: String,
    imageRepository: ImageRepository = ImageRepository()
): State<Result<Image>> {

    // Creates a State<T> with Result.Loading as initial value
    // If either `url` or `imageRepository` changes, the running producer
    // will cancel and will be re-launched with the new inputs.
    return produceState<Result<Image>>(initialValue = Result.Loading, url, imageRepository) {

        // In a coroutine, can make suspend calls
        val image = imageRepository.load(url)

        // Update State with either an Error or Success result.
        // This will trigger a recomposition where this State is read
        value = if (image == null) {
            Result.Error
        } else {
            Result.Success(image)
        }
    }
}*/


//하나이상의 상태 객체를 다른 상태로 변환
//이를 통해 불필요한 Recomposition을 줄이고 계산되값이 바뀔때만 State의 value가 바뀜
/*@Composable
// When the messages parameter changes, the MessageList
// composable recomposes. derivedStateOf does not
// affect this recomposition.
fun DerivedStateOfExample(messages: List<Message>) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            // ...
        }

        // Show the button if the first visible item is past
        // the first item. We use a remembered derived state to
        // minimize unnecessary compositions
        val showButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        AnimatedVisibility(visible = showButton) {
            ScrollToTopButton()
        }
    }
}*/


//Compose의 상태를 flow로 변환
@Composable
fun SnapShotFlowExample(){
    val listState : LazyListState = rememberLazyListState()

    LazyColumn(state = listState) {
        // ...
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            //State<T> -> Flow<T>
            //이전에 방출한 값과 다를 경우 방출
            .map { index -> index > 0 }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                //MyAnalyticsService.sendScrolledPastFirstItemEvent()
            }
    }
}

@Composable
fun SideEffectsTest() {
    val component = remember {
        mutableStateOf(1)
    }
    Log.d("TAG", "RememberUpdateStateExample2: out LaunchedEffect ${component.component1()}")
    Button(onClick = { component.component2()(component.component1() + 1) }) {
        Text(text = "Click")
    }
    LaunchedEffect(key1 = true) {
        delay(10000L)
        Log.d("TAG", "RememberUpdateStateExample2: in LaunchedEffect ${component.component1()}")
    }
    LaunchedEffect(component.value) {
        Log.d("TAG", "RememberUpdateStateExample2: in first LaunchedEffect ${component.component1()}")
        delay(10000L)
        Log.d("TAG", "RememberUpdateStateExample2: in second LaunchedEffect ${component.component1()}")
    }
}