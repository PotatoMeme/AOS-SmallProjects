package com.potatomeme.sample_clipboard_webview.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.potatomeme.sample_clipboard_webview.Clipboards
import com.potatomeme.sample_clipboard_webview.data.model.ClipboardState
import com.potatomeme.sample_clipboard_webview.data.model.toClipboardState
import com.potatomeme.sample_clipboard_webview.data.model.toDataStoreFormat
import com.potatomeme.sample_clipboard_webview.data.serializer.ClipboardsSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.clipboardProtoDataStore: DataStore<Clipboards>
        by dataStore(
            fileName = "clipboardSettings.pb",
            serializer = ClipboardsSerializer
        )

class ClipboardDataStoreSource private constructor(context: Context) {
    private val clipboardProtoDataStore = context.clipboardProtoDataStore


    val clipboardsFlow: Flow<List<ClipboardState>> =
        clipboardProtoDataStore.data.map { clipboards: Clipboards ->
            clipboards.clipboardList.map {
                it.toClipboardState()
            }
        }


    suspend fun addClipBoard(clipboardState: ClipboardState) {
        clipboardProtoDataStore.updateData { currentSettings: Clipboards ->
            currentSettings.toBuilder()
                .addClipboard(clipboardState.toDataStoreFormat())
                .build()
        }
    }

    suspend fun refreshClipBoard() {
        clipboardProtoDataStore.updateData { currentSettings: Clipboards ->
            currentSettings.toBuilder()
                .clearClipboard()
                .build()
        }
    }

    companion object {
        private var dataStoreDataSource: ClipboardDataStoreSource? = null

        fun getInstance(context: Context): ClipboardDataStoreSource {
            return dataStoreDataSource ?: synchronized(this) {
                dataStoreDataSource ?: ClipboardDataStoreSource(context).also {
                    dataStoreDataSource = it
                }
            }
        }
    }
}