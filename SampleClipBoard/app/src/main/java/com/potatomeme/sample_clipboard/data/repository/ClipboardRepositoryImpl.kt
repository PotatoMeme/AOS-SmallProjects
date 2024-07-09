package com.potatomeme.sample_clipboard.data.repository

import android.content.Context
import com.potatomeme.sample_clipboard.data.model.ClipboardState
import com.potatomeme.sample_clipboard.data.model.StopWatchState
import com.potatomeme.sample_clipboard.data.source.ClipboardDataStoreSource
import kotlinx.coroutines.flow.Flow

class ClipboardRepositoryImpl private constructor(private val dataStoreSource:ClipboardDataStoreSource) : ClipboardRepository {
    override fun getClipboardsFlow(): Flow<List<ClipboardState>> {
        return dataStoreSource.clipboardsFlow
    }

    override fun getStopWatchCountFlow(): Flow<Long> {
        return dataStoreSource.stopWatchCountFlow
    }

    override fun getStopWatchStateFlow(): Flow<StopWatchState> {
        return dataStoreSource.stopWatchStateFlow
    }

    override suspend fun addClipBoard(clipboardState: ClipboardState) {
        dataStoreSource.addClipBoard(clipboardState)
    }

    override suspend fun refreshClipBoard() {
        dataStoreSource.refreshClipBoard()
    }

    override suspend fun refreshStopWatch() {
        dataStoreSource.refreshStopWatch()
    }

    override suspend fun countStopWatch(longNum: Long) {
        dataStoreSource.countStopWatch(longNum)
    }

    override suspend fun stopWatchStateChanged(state: StopWatchState) {
        dataStoreSource.stopWatchStateChanged(state)
    }

    companion object {
        @Volatile
        private var clipBoardRepository: ClipboardRepositoryImpl? = null
        fun getInstance(dataStoreSource: ClipboardDataStoreSource): ClipboardRepositoryImpl {
            return clipBoardRepository ?: synchronized(this) {
                clipBoardRepository ?: ClipboardRepositoryImpl(dataStoreSource).also {
                    clipBoardRepository = it
                }
            }
        }
    }
}