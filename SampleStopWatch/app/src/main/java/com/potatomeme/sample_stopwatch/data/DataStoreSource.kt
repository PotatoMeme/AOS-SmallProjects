package com.potatomeme.sample_stopwatch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.potatomeme.sample_stopwatch.data.StopWatchState.Companion.STOP_WATCH_STATE_PAUSE
import com.potatomeme.sample_stopwatch.data.StopWatchState.Companion.STOP_WATCH_STATE_PLAY
import com.potatomeme.sample_stopwatch.data.StopWatchState.Companion.STOP_WATCH_STATE_STOP
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.IllegalArgumentException

val Context.preferencesDataStore: DataStore<Preferences>
        by preferencesDataStore(name = "StopWatch")

sealed class StopWatchState {
    data object Stop : StopWatchState()
    data object Play : StopWatchState()
    data object Pause : StopWatchState()
    companion object{
        const val STOP_WATCH_STATE_STOP = 0
        const val STOP_WATCH_STATE_PLAY = 1
        const val STOP_WATCH_STATE_PAUSE = 2

        fun fromInt(value: Int): StopWatchState {
            return when (value) {
                STOP_WATCH_STATE_STOP -> Stop
                STOP_WATCH_STATE_PLAY -> Play
                STOP_WATCH_STATE_PAUSE -> Pause
                else -> throw IllegalArgumentException("Invalid StopWatchState value: $value")
            }
        }

        fun toInt(state: StopWatchState): Int {
            return when (state) {
                Stop -> STOP_WATCH_STATE_STOP
                Play -> STOP_WATCH_STATE_PLAY
                Pause -> STOP_WATCH_STATE_PAUSE
            }
        }
    }
}

class DataStoreSource(context: Context) {
    private val preferencesDataStore = context.preferencesDataStore
    val stopWatchCountFlow: Flow<Long> = preferencesDataStore.data
        .map { preferences ->
            preferences[STOP_WATCH_COUNT] ?: 0L
        }

    val stopWatchStateFlow: Flow<StopWatchState> = preferencesDataStore.data
        .map { preferences ->
            val state = preferences[STOP_WATCH_STATE] ?: STOP_WATCH_STATE_STOP
            StopWatchState.fromInt(state)
        }

    suspend fun refreshStopWatch() {
        preferencesDataStore.edit { preferences ->
            preferences[STOP_WATCH_STATE] = STOP_WATCH_STATE_STOP
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

        private var dataStoreDataSource: DataStoreSource? = null
        fun getInstance(context: Context): DataStoreSource {
            if (dataStoreDataSource == null) {
                dataStoreDataSource = DataStoreSource(context)
            }
            return dataStoreDataSource!!
        }
    }
}

