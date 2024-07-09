package com.potatomeme.sample_clipboard.ui

import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.potatomeme.sample_clipboard.data.model.ClipboardState
import com.potatomeme.sample_clipboard.data.model.StopWatchState
import com.potatomeme.sample_clipboard.data.repository.ClipboardRepository
import com.potatomeme.sample_clipboard.data.repository.ClipboardRepositoryImpl
import com.potatomeme.sample_clipboard.data.source.ClipboardDataStoreSource
import com.potatomeme.sample_clipboard.worker.ClipboardWorker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(application: Application) : ViewModel() {
    private val workManager: WorkManager = WorkManager.getInstance(application)
    private val repository: ClipboardRepository

    val clipboardsFlow: StateFlow<List<ClipboardState>>
    val countFlow: StateFlow<Long>
    val stateFlow: StateFlow<StopWatchState>

    init {
        val dataSource = ClipboardDataStoreSource.getInstance(application)
        repository = ClipboardRepositoryImpl.getInstance(dataSource)

        clipboardsFlow = repository.getClipboardsFlow().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

        countFlow = repository.getStopWatchCountFlow().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0L
        )

        stateFlow = repository.getStopWatchStateFlow().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StopWatchState.Stop
        )
    }

    fun enqueueClipboardWorker() {
        clipBoardDiff()
        val request = OneTimeWorkRequestBuilder<ClipboardWorker>()
            .build()

        workManager.enqueueUniqueWork(
            "Clipboard",
            ExistingWorkPolicy.KEEP,
            request
        )
    }

    fun cancelWorker() = viewModelScope.launch {
        workManager.cancelUniqueWork("Clipboard")
        repository.refreshStopWatch()
    }

    private val clipboardManager: ClipboardManager =
        application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    fun clipBoardDiff() {
        Log.d(TAG, "clipBoardDiff: ${(clipboardManager.primaryClip?.itemCount)}")
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