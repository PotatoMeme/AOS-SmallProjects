package com.potatomeme.sample_stopwatch.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
import com.potatomeme.sample_stopwatch.data.DataStoreSource
import com.potatomeme.sample_stopwatch.data.StopWatchState
import com.potatomeme.sample_stopwatch.worker.StopWatchWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(application: Application) : ViewModel() {
    private val workManager: WorkManager = WorkManager.getInstance(application)
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

    val outputWorkerInfo : Flow<List<WorkInfo>> = workManager.getWorkInfosFlow(
        WorkQuery.Builder
            .fromTags(listOf(WORK_TAG))
            //.addStates(listOf(WorkInfo.State.FAILED, WorkInfo.State.CANCELLED))
            .addUniqueWorkNames(listOf(WORK_NAME))
            .build()
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
     * in WorkManager
     */
    fun enqueueStopWatch(){
        val  request = OneTimeWorkRequestBuilder<StopWatchWorker>()
            .build()

        workManager.enqueueUniqueWork(
            "StopWatch",
            // ExistingWorkPolicy : (일회성 작업) 같은 고유 이름을 가진 작업과 충돌할 경우 동작 정책을 나타내는 enum 값
            // REPLACE ( 기존 작업을 취소 새 작업 대체 )
            // KEEP ( 새 작업을 무시 기존 작업 유지 )
            // APPEND ( 새 작업을 기존 작업의 끝에 추가, 기존 작업이 취소 실패시 새 작업도 취소 실패 )
            // APPEND_OR_REPLACE ( 새 작업을 기존 작업의 끝에 추가, 기존 작업이 취소 실패시 새 작업은 실행 )
            ExistingWorkPolicy.KEEP,
            request
        )
    }

    /**
     * in ViewModel
     * */
    /*fun enqueueStopWatch() = viewModelScope.launch {
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
    }*/

    companion object{
        private const val TAG = "MainViewModel"

        private const val WORK_NAME = "unique WorkName"
        private const val WORK_TAG = "unique WorkTag"
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