package com.potatomeme.sample_clipboard_webview.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.potatomeme.sample_clipboard_webview.data.model.ClipboardState
import com.potatomeme.sample_clipboard_webview.data.repository.ClipboardRepository
import com.potatomeme.sample_clipboard_webview.data.repository.ClipboardRepositoryImpl
import com.potatomeme.sample_clipboard_webview.data.source.ClipboardDataStoreSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(private val repository: ClipboardRepository) : ViewModel() {
    //clipboard flow
    val clipboardsFlow: StateFlow<List<ClipboardState>> = repository.getClipboardsFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )

    fun addClipboardImageData(
        value : String = "",
        time : String = "0000:00:00",
        reference: String = ""
    )=viewModelScope.launch {
        val clipboardState = ClipboardState.ImageClipBoard(
            value, time, reference
        )
        repository.addClipBoard(clipboardState)
    }

    fun addClipboardTextData(
        value : String = "",
        time : String = "0000:00:00",
        reference: String = ""
    )=viewModelScope.launch {
        val clipboardState = ClipboardState.TextClipBoard(
            value, time, reference
        )
        repository.addClipBoard(clipboardState)
    }

    fun refreshClipboard() = viewModelScope.launch {
        repository.refreshClipBoard()
    }
}


class MainViewModelFactory(application: Application) : ViewModelProvider.Factory {
    private val dataSource = ClipboardDataStoreSource.getInstance(application)
    private val repository: ClipboardRepository = ClipboardRepositoryImpl.getInstance(dataSource)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}