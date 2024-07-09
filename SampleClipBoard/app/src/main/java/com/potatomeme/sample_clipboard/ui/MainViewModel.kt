package com.potatomeme.sample_clipboard.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.potatomeme.sample_clipboard.data.model.ClipboardState
import com.potatomeme.sample_clipboard.data.model.StopWatchState
import com.potatomeme.sample_clipboard.data.repository.ClipboardRepository
import com.potatomeme.sample_clipboard.data.repository.ClipboardRepositoryImpl
import com.potatomeme.sample_clipboard.data.source.ClipboardDataStoreSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.lang.IllegalArgumentException

class MainViewModel(application: Application) : ViewModel() {
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

}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}