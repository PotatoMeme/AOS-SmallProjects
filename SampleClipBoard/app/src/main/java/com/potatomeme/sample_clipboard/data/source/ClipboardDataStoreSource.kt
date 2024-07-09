package com.potatomeme.sample_clipboard.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.potatomeme.sample_clipboard.Clipboards
import com.potatomeme.sample_clipboard.data.model.ClipboardState
import com.potatomeme.sample_clipboard.data.model.StopWatchState
import com.potatomeme.sample_clipboard.data.model.toClipboardState
import com.potatomeme.sample_clipboard.data.model.toDataStoreFormat
import com.potatomeme.sample_clipboard.data.serializer.ClipboardsSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.preferencesDataStore: DataStore<Preferences>
        by preferencesDataStore(name = "Clipboard")

val Context.clipboardProtoDataStore: DataStore<Clipboards>
        by dataStore(
            fileName = "clipboardSettings.pb",
            serializer = ClipboardsSerializer
        )

class ClipboardDataStoreSource private constructor(context: Context) {
    private val clipboardProtoDataStore = context.clipboardProtoDataStore
    private val preferencesDataStore = context.preferencesDataStore


    val clipboardsFlow: Flow<List<ClipboardState>> =
        clipboardProtoDataStore.data.map { clipboards: Clipboards ->
            clipboards.clipboardList.map {
                it.toClipboardState()
            }
        }

    val stopWatchCountFlow: Flow<Long> = preferencesDataStore.data
        .map { preferences ->
            preferences[STOP_WATCH_COUNT] ?: 0L
        }

    val stopWatchStateFlow: Flow<StopWatchState> = preferencesDataStore.data
        .map { preferences ->
            val state = preferences[STOP_WATCH_STATE] ?: StopWatchState.STATE_STOP
            StopWatchState.fromInt(state)
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

    suspend fun refreshStopWatch() {
        preferencesDataStore.edit { preferences ->
            preferences[STOP_WATCH_STATE] = StopWatchState.STATE_STOP
            preferences[STOP_WATCH_COUNT] = 0L
        }
    }

    suspend fun countStopWatch(longNum: Long) {
        preferencesDataStore.edit { preferences ->
            val currentValue = preferences[STOP_WATCH_COUNT] ?: 0L
            preferences[STOP_WATCH_COUNT] = currentValue + longNum
        }
    }

    suspend fun stopWatchStateChanged(state: StopWatchState) {
        preferencesDataStore.edit { preferences ->
            preferences[STOP_WATCH_STATE] = StopWatchState.toInt(state)
        }
    }

    companion object {
        private val STOP_WATCH_COUNT = longPreferencesKey("STOP_WATCH_COUNT")
        private val STOP_WATCH_STATE = intPreferencesKey("STOP_WATCH_STATE")

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