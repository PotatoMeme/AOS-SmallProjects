package com.potatomeme.sample_clipboard_webview.data.repository

import com.potatomeme.sample_clipboard_webview.data.model.ClipboardState
import kotlinx.coroutines.flow.Flow

interface ClipboardRepository {
    fun getClipboardsFlow(): Flow<List<ClipboardState>>
    suspend fun addClipBoard(clipboardState: ClipboardState)
    suspend fun refreshClipBoard()
}