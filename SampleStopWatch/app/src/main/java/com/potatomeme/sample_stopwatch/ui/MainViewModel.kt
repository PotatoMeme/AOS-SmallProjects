package com.potatomeme.sample_stopwatch.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.potatomeme.sample_stopwatch.data.DataStoreSource
import com.potatomeme.sample_stopwatch.data.StopWatchState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(application: Application) : ViewModel() {
    //private val workManager: WorkManager = WorkManager.getInstance(application)
    private val dataStoreDataSource = DataStoreSource.getInstance(application)

    val countFlow: StateFlow<Long> = dataStoreDataSource.stopWatchCountFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0L
    )

    val stateFlow: StateFlow<StopWatchState> = dataStoreDataSource.stopWatchStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StopWatchState.Stop
    )

    init {
        // StateFlow 값 변경 로그 추가
        viewModelScope.launch {
            stateFlow.collect { state ->
                Log.d(TAG, "StateFlow Updated: $state")
            }
        }
    }

    /**
     * in ViewModel
     * */
    fun enqueueStopWatch() = viewModelScope.launch {
        if(stateFlow.value == StopWatchState.Play) return@launch
        Log.d(TAG, "enqueueStopWatch: 1 : ${stateFlow.value}")
        dataStoreDataSource.stopWatchStateChanged(StopWatchState.Play)
        Log.d(TAG, "enqueueStopWatch: 2 : ${stateFlow.value}")
        var pastTime = System.currentTimeMillis()
        while (stateFlow.value == StopWatchState.Play) {
            val currentTime = System.currentTimeMillis()
            val diffTime = currentTime - pastTime
            dataStoreDataSource.countStopWatch(diffTime)
            pastTime = currentTime
            delay(100)
        }
    }

    fun stopStopWatch() = viewModelScope.launch {
        dataStoreDataSource.refreshStopWatch()
    }

    fun pauseStopWatch() = viewModelScope.launch {
        dataStoreDataSource.stopWatchStateChanged(StopWatchState.Stop)
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}