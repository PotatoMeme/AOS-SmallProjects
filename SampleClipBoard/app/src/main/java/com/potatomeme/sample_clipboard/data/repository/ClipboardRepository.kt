package com.potatomeme.sample_clipboard.data.repository

import android.content.Context
import com.potatomeme.sample_clipboard.data.model.ClipboardState
import com.potatomeme.sample_clipboard.data.model.StopWatchState
import kotlinx.coroutines.flow.Flow

interface ClipboardRepository {
    suspend fun getClipboardsFlow(): Flow<List<ClipboardState>>
    suspend fun getStopWatchCountFlow(): Flow<Long>
    suspend fun getStopWatchStateFlow(): Flow<StopWatchState>
    suspend fun addClipBoard(clipboardState: ClipboardState)
    suspend fun refreshClipBoard()
    suspend fun refreshStopWatch()
    suspend fun countStopWatch(longNum: Long)
    suspend fun stopWatchStateChanged(state: StopWatchState)
}