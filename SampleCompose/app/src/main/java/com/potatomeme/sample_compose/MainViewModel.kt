package com.potatomeme.sample_compose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _text1: MutableState<String> = mutableStateOf("")
    val text1: State<String>
        get() = _text1

    val onText1Submit: (String) -> Unit = { str ->
        Log.d(TAG, "onTextSubmit before text : ${text1.value}")
        _text1.value = str
        Log.d(TAG, "onTextSubmit now text : ${text1.value}")
    }

    private val _text2 : MutableLiveData<String> = MutableLiveData("")
    val text2 : LiveData<String>
        get() = _text2

    val onText2Submit: (String) -> Unit = { str ->
        Log.d(TAG, "onText2Submit before text : ${text2.value}")
        _text2.value = str
        Log.d(TAG, "onText2Submit now text : ${text2.value}")
    }

    @Composable
    fun useText2ToState(){
        text2.observeAsState()
    }



    private val _text3 : MutableStateFlow<String> = MutableStateFlow("")
    val text3 : StateFlow<String>
        get() = _text3

    val onText3Submit: (String) -> Unit = { str ->
        Log.d(TAG, "onText3Submit before text : ${text3.value}")
        _text3.value = str
        //text3.collectAsState()
        Log.d(TAG, "onText3Submit now text : ${text3.value}")
    }
    
    val list1 : SnapshotStateList<String> = mutableStateListOf()


    companion object {
        private const val TAG = "MainViewModel"
    }

}