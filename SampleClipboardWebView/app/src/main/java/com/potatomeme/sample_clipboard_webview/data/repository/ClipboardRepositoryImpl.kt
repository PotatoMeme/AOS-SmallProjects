package com.potatomeme.sample_clipboard_webview.data.repository

import com.potatomeme.sample_clipboard_webview.data.model.ClipboardState
import com.potatomeme.sample_clipboard_webview.data.source.ClipboardDataStoreSource
import kotlinx.coroutines.flow.Flow

class ClipboardRepositoryImpl private constructor(private val dataStoreSource: ClipboardDataStoreSource) :
    ClipboardRepository {
    override fun getClipboardsFlow(): Flow<List<ClipboardState>> {
        return dataStoreSource.clipboardsFlow
    }
    override suspend fun addClipBoard(clipboardState: ClipboardState) {
        dataStoreSource.addClipBoard(clipboardState)
    }

    override suspend fun refreshClipBoard() {
        dataStoreSource.refreshClipBoard()
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